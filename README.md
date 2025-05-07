<h1 align="center"> Full Stack Movie App 🎬 | BACKEND </h1> <br>

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" alt="React" width="50" height="50"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring Boot" width="50" height="50"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="50" height="50"/>
</p>






<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [Features](#features)
- [How to install and run](#how-to-install-and-run)
  


<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction 


This project is  a Backend of a fullstack project. 

<p>
  This project is a <strong>backend</strong>for a movie streaming website. It was built solely for <strong>educational and personal learning purposes</strong>, with no intention of distributing or linking to copyrighted content.
</p>

<p>
  Through this project, I aimed to:
</p>

<ul>
  <li>Improve my skills in Spring Boot, Java and PostgreSQL.</li>
  <li>Create a code structure where we can add any feature we want for future features.</li>
</ul>

<p>
  This clone focuses purely on the <strong>Backend experience</strong>, simulating the look and feel of a real movie streaming site — without streaming functionality.
</p>


## Dependencies

* JPA
* Lombok
* Postgresql
* JWT
* Thymeleaf

## How to install and run

<h3>🧱 Step 1: Create a PostgreSQL Database</h3>
<p>
  Open your PostgreSQL server and create a new database named 
  <strong>Movie Website</strong> <br>
  (You can use another name, but make sure to update it in the 
  <code>application.properties</code> file — see Step 2).
</p>

<br>

<p align="center">
  <img src="https://github.com/user-attachments/assets/3178a90f-3313-4373-a322-91bd075564d3" 
       alt="Create Database Screenshot" 
       width="500"/>
</p>

<hr>

<h3>⚙️ Step 2: Configure Database Connection</h3>

<p>
  Navigate to: <br>
  <code>src/main/resources/application.properties</code>
</p>

<p>
  Paste and configure the following properties (replace placeholders with your actual PostgreSQL credentials):
</p>

<pre>
<code>
spring.application.name=movie_app

spring.datasource.url=jdbc:postgresql://localhost:5432/Movie Website
spring.datasource.username=YOUR_POSTGRES_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

logging.level.org.springframework.security=DEBUG
</code>
</pre>

<p>
  ✅ <em>Make sure the database name matches the one you created in Step 1.</em>
</p>
<hr>
<h3>📦 Step 3: Add Example Data to Your Database</h3>

<p>
  You can populate your PostgreSQL database with example data using the 
  <code>movie_db_example_data.backup</code> file included in this project.
</p>

<p>
  Follow these steps to import the backup:
</p>

<ol>
  <li>Open <strong>pgAdmin</strong> and connect to your PostgreSQL server.</li>
  <li>Right-click on your target database (e.g. <code>Movie Website</code>) and select <strong>Restore</strong>.</li>
  <li>In the dialog box:
    <ul>
      <li>Set <strong>Format</strong> to <code>Custom or tar</code>.</li>
      <li>For <strong>Filename</strong>, choose the <code>movie_db_example_data.backup</code> file from your local machine.</li>
    </ul>
  </li>
  <li>Click <strong>Restore</strong> to begin the import process.</li>
</ol>

<p>
  ✅ Once the restore is complete, your database will be populated with sample movies, tags, watch options, and more.
</p>
<hr>
<h3>📦 (OPTIONAL) Step 4: Import Postman Endpoints into Postman App</h3>

<p>
  For a faster and more efficient development experience, you can import ready-to-use API endpoints into your Postman app using the 
  <code>Movie App.postman_collection.json</code> file included in this project.
</p>

<p>
  Follow these steps:
</p>

<ol>
  <li>Open your <strong>Postman</strong> app.</li>
  <li>Click on the <strong>Import</strong> button (usually top-left corner).</li>
  <li>In the dialog that appears, choose the <strong>File</strong> tab.</li>
  <li>Select the <code>Movie App.postman_collection.json</code> file from your local machine.</li>
  <li>Click <strong>Import</strong>.</li>
</ol>

<p>
  ✅ The collection will now appear in your Postman workspace, with all available endpoints (GET, POST, PUT, DELETE, etc.) ready to test.
</p>



## Features
![11](https://github.com/user-attachments/assets/40d18242-0a72-46f5-8212-ed1c738c99ee) <br>
![12](https://github.com/user-attachments/assets/5a3948c0-d360-45e1-b713-d6e1e655f49f)

