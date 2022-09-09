package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) == null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) != null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
            if (accounts.containsKey(fromId) && accounts.containsKey(toId)
                    && accounts.get(fromId).amount() >= amount && amount > 0) {
                var accountFrom = accounts.get(fromId);
                var accountTo = accounts.get(toId);
                update(new Account(fromId, accountFrom.amount() - amount));
                update(new Account(toId, accountTo.amount() + amount));
                rsl = true;
            }
            return rsl;
        }
    }
}

