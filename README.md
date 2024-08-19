
Laboration 2 - Habit Tracker App

📱 Project Overview
Laboration 2 is a habit-tracking app developed as part of the course led by Björne. The app is designed to help users build and maintain positive habits by allowing them to track their progress over time. The project demonstrates the integration of Android development using Kotlin and cloud services via AWS CDK for backend infrastructure.

🎯 Features
Habit Creation: Users can create habits they want to track, set goals, and customize habit details.
Daily/Weekly Tracking: Track progress daily or weekly with visual feedback on progress.
Reminders: Set reminders to stay on top of your habits.
Statistics and Analytics: View detailed statistics and insights on your habit-forming journey.
Cloud Integration: AWS services are used to manage and store habit data securely.
🛠️ Technologies Used
Kotlin: Primary programming language for Android development.
Android Studio: Integrated development environment (IDE) for building the app.
AWS CDK (Cloud Development Kit): Used to define cloud infrastructure in code using Java.
AWS Services:
AWS Lambda: Serverless compute for backend processing.
Amazon DynamoDB: NoSQL database for storing habit data.
Amazon SNS (Simple Notification Service): For sending reminders.
AWS S3: For storing user-related media files.
Jetpack Compose: Modern UI toolkit for building native Android UIs.
🚀 Getting Started
Prerequisites
Android Studio: Install the latest version from here.
AWS CLI: Install the AWS Command Line Interface from here.
IntelliJ IDEA: Recommended for managing AWS CDK and backend development. Install from here.
Installation
Clone the repository:

bash
Kopiera kod
git clone https://github.com/undvall/laboration2.git
cd laboration2
Set up the Android project:

Open the project in Android Studio.
Sync the Gradle files to download dependencies.
Set up an Android Virtual Device (AVD) or connect a physical device for testing.
Set up AWS CDK project:

Open the cdk/ directory in IntelliJ IDEA.
Install the required dependencies:
bash
Kopiera kod
mvn install
Deploy the AWS infrastructure:
bash
Kopiera kod
cdk deploy
Run the app:

In Android Studio, select your device and run the app.
📈 Usage
Creating a Habit: Tap on the "Add Habit" button, enter details like habit name, frequency, and goals.
Tracking Progress: Mark habits as completed each day, and monitor progress on the dashboard.
View Statistics: Navigate to the statistics page to see insights into your habit performance over time.
📖 Documentation
API Documentation: Link to your API documentation if you have one.
AWS CDK Documentation: Brief guide on how the AWS resources are structured and how they interact with the app.
🛡️ License
This project is licensed under the MIT License. See the LICENSE file for details.

👥 Contributors
Your Name - undvall
Björne - Course instructor