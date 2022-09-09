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
            accounts.putIfAbsent(account.id(), account);
            return Objects.equals(account, accounts.get(account.id()));
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            accounts.put(account.id(), account);
            return Objects.equals(account, accounts.get(account.id()));
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
            return accounts.containsKey(id);
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
            }
            return rsl;
        }
    }
}

