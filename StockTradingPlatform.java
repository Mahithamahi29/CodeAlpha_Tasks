import java.io.*;
import java.util.*;

// -------- Stock Class --------
class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public void updatePrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + " (" + name + ") - Rs." + price;
    }
}

// -------- Transaction Class --------
class Transaction {
    String type; // BUY or SELL
    String symbol;
    int quantity;
    double price;
    Date date;

    public Transaction(String type, String symbol, int quantity, double price) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return date + " | " + type + " | " + symbol + " | Qty: " + quantity + " | Rs." + price;
    }
}

// -------- User / Portfolio Class --------
class User {
    private String username;
    private double balance;
    private Map<String, Integer> portfolio;
    private List<Transaction> transactions;

    public User(String username, double balance) {
        this.username = username;
        this.balance = balance;
        portfolio = new HashMap<>();
        transactions = new ArrayList<>();
    }

    public double getBalance() { return balance; }

    public void buyStock(Stock stock, int qty) {
        double cost = stock.getPrice() * qty;
        if (cost <= balance) {
            balance -= cost;
            portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + qty);
            transactions.add(new Transaction("BUY", stock.getSymbol(), qty, stock.getPrice()));
            System.out.println("Stock bought successfully!");
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void sellStock(Stock stock, int qty) {
        int owned = portfolio.getOrDefault(stock.getSymbol(), 0);
        if (qty <= owned) {
            balance += stock.getPrice() * qty;
            portfolio.put(stock.getSymbol(), owned - qty);
            transactions.add(new Transaction("SELL", stock.getSymbol(), qty, stock.getPrice()));
            System.out.println("Stock sold successfully!");
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio ---");
        double totalValue = balance;
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            Stock s = market.get(symbol);
            double value = qty * s.getPrice();
            totalValue += value;
            System.out.println(symbol + " | Qty: " + qty + " | Value: Rs." + value);
        }
        System.out.println("Cash Balance: Rs." + balance);
        System.out.println("Total Portfolio Value: Rs." + totalValue);
    }

    public void viewTransactions() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Save portfolio to file
    public void saveToFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("portfolio.dat"));
        out.writeObject(portfolio);
        out.writeDouble(balance);
        out.close();
    }
}

// -------- Main Trading Platform --------
public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Market data
        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500));
        market.put("INFY", new Stock("INFY", "Infosys", 1450));
        market.put("RELI", new Stock("RELI", "Reliance", 2500));

        User user = new User("Mahitha", 100000);

        while (true) {
            System.out.println("\n===== Stock Trading Platform =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock s : market.values()) {
                        System.out.println(s);
                    }
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol: ");
                    String buySym = sc.next();
                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();
                    if (market.containsKey(buySym)) {
                        user.buyStock(market.get(buySym), buyQty);
                    } else {
                        System.out.println("Invalid stock symbol!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol: ");
                    String sellSym = sc.next();
                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();
                    if (market.containsKey(sellSym)) {
                        user.sellStock(market.get(sellSym), sellQty);
                    } else {
                        System.out.println("Invalid stock symbol!");
                    }
                    break;

                case 4:
                    user.viewPortfolio(market);
                    break;

                case 5:
                    user.viewTransactions();
                    break;

                case 6:
                    System.out.println("Thank you for trading!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
