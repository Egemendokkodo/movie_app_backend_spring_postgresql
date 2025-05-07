<h1 align="center"> Full Stack Movie App 🎬 | BACKEND </h1> <br>

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" alt="React" width="50" height="50"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring Boot" width="50" height="50"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="50" height="50"/>
</p>

<h2 align="center">
 ⚠️ <strong>Important:</strong> This is a BACKEND for a Full Stack project. To install and run this project properly, please check <a href="https://github.com/Egemendokkodo/movie_app_backend_spring_postgresql">this project's backend repository</a> first.
  <br></br></h2>
</h2>





<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction) 
- [Features](#features)
- [How to install and run](#how-to-install-and-run)
  


<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction 


This project is  a Backend of a fullstack project. 

<p>
  This project is a <strong>backend</strong> of the popular movie streaming website <em>HDFilmCehennemi</em>. It was built solely for <strong>educational and personal learning purposes</strong>, with no intention of distributing or linking to copyrighted content.
</p>

<p>
  Through this project, I aimed to:
</p>

<ul>
  <li>Improve my skills in Spring Boot, Java and PostgreSQL.</li>
  <li>Create a code structure where we can add any feature we want for future features.</li>
</ul>

<p>
  This clone focuses purely on the <strong>Backend experience</strong>, simulating the look and feel of a real movie streaming site — without any backend or streaming functionality.
</p>

<blockquote>
  ⚠️ <strong>Disclaimer:</strong> This is a UI-only project. It does not host, stream, or promote any pirated or copyrighted content.
</blockquote>

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


## Features

* Reusable template
* Satisfying and Modern UI
* A collection of very nice UX features like hover inspection on movie, featured movies, sliders etc..
* Detailed movie search. With this option, you can cherry-pick the movie you desire.
* <strong>Admin Dashboard!</strong>

