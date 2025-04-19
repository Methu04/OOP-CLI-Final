# 🎟️ Ticket Management System – Java Console App

A **Java-based multithreaded CLI application** that simulates a ticketing system with dynamic vendor release and customer purchase behaviors. Built using core Java concepts like Threads, Synchronization, JSON handling, and file I/O.

---

## 📌 Features

- 🧾 Configurable ticket pool via JSON
- 🚀 Vendors release tickets at configurable rates
- 🧍 Customers retrieve tickets concurrently
- 🔐 Thread-safe access to shared ticket pool
- 📁 Logs activity to `Log.json` file
- 💾 Configuration saved to `config.json`
- ⏱ Graceful thread interruption and shutdown

---

## 🛠 Technologies Used

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
- Gson (for JSON serialization)
- Java Threads (`Runnable`, `Thread`)
- Synchronized blocks & wait/notify
- CLI-based Interaction

---
## ✅ How It Works

1. 👨‍💻 User creates or loads a ticket configuration:
   - `totalTickets`, `releaseRate`, `customerRetrievalRate`, and `maxTicketCapacity`
2. 🧑‍💼 Vendors (10 threads) generate tickets every second
3. 🧍 Customers (10 threads) purchase tickets based on retrieval rate
4. 💾 Every action (add/remove) is logged in `Log.json`
5. 🛑 After 10 seconds, all threads are gracefully interrupted

---

## 🚀 How to Run

### 1️⃣ Compile the project
```bash
javac -d out src/main/java/Classes/*.java src/main/java/TicketingApp.java
```

### 2️⃣ Run the application
```bash
java -cp out TicketingApp
```

---
## 📚 Concepts Practiced

- Thread management and synchronization

- JSON serialization with Gson

- Java I/O (FileWriter, FileReader)

- Multithreaded simulation design

- Modular OOP design (classes split cleanly)


