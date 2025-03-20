# F1 Database Project

## 📌 Overview
The **F1 Database** is a Java-based application that allows users to view and search Formula 1 drivers and teams. It includes a graphical user interface (GUI) built with **Swing** and leverages **Maven** for dependency management. The backend loads and processes CSV data, and JUnit tests ensure functionality.

## 📁 Project Structure
```
Da-Team-Project/
├── src/
│   ├── main/java/com/example/
│   │   ├── Backend.java
│   │   ├── Frontend.java
│   ├── main/resources/csv/
│   │   ├── Drivers.csv
│   │   ├── Teams.csv
│   ├── test/java/
│   │   ├── TestingBackend.java
│   │   ├── TestingFrontend.java
│   ├── test/resources/csv/
│   │   ├── test.csv
├── pom.xml
└── README.md
```
*Subject to change over time.*

## 🔧 Installation & Setup
### **1. Prerequisites**
- Java **17+** installed ([Download JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html))
- **Apache Maven** installed ([Download Maven](https://maven.apache.org/download.cgi))

### **2. Clone the Repository**
```sh
git clone https://github.com/HappyGold11/Da-Team-Project
cd Da-Team-Project
```

### **3. Build the Project**
Run the following command to compile the project and install dependencies:
```sh
mvn clean install
```

### **4. Run the Application**
To start the GUI application, execute the appropriate script based on your OS.

## 🛠 Features
✅ **Search F1 drivers and teams**  
✅ **Bookmark favorite drivers and teams**  
✅ **JUnit tests for backend and frontend**  
✅ **CSV-based data management**  
✅ **Swing-based GUI**  

## 🧪 Running Tests
To execute unit tests, run:
```sh
mvn test
```
If you get **CSV loading errors**, ensure test CSVs are in `src/test/resources/csv/`.

## 📌 Troubleshooting
### **Error: Could not find or load main class Frontend**
✔ Ensure the project is built using:
```sh
mvn clean install
```

## 🚀 Build & Deploy Scripts

### **Windows (`build.bat`)**

1. Place `build.bat` in the project root.
2. Open **Command Prompt**, navigate to the project directory, and run:
   ```cmd
   build.bat
   ```

**Actions:**
- Builds the project
- Runs the JAR file

### **Unix/Linux/macOS (`build.sh`)**

1. Place `build.sh` in the project root.
2. Grant execute permission and run:
   ```sh
   chmod +x build-run-deploy.sh
   ./build-run-deploy.sh
   ```

**Actions:**
- Builds the project
- Runs the JAR file

