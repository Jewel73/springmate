# SpringMate Framework

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.springmate/springmate-framework.svg)](https://search.maven.org/search?q=g:com.springmate)

SpringMate is a powerful and flexible message processing framework for Spring Boot applications that simplifies REST API development by providing a standardized way to handle requests, responses, and common patterns.

## 🚀 Features

- ✨ Generic Message Processing
- 🔍 Advanced Dynamic Filtering
- 📄 Automatic Pagination
- 🎯 Service Auto-Discovery
- 💾 Built-in Caching Support
- ✅ Validation Framework
- 🔒 Error Handling
- 📊 Transaction Management
- 🧪 Comprehensive Testing Support

## 📦 Installation

### Maven
```xml
<dependency>
    <groupId>com.springmate</groupId>
    <artifactId>springmate-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
implementation 'com.springmate:springmate-framework:1.0.0'
```

## 🔧 Quick Start

1. Add the dependency to your project
2. Enable SpringMate in your application:

```java
@SpringBootApplication
@EnableSpringMate
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

3. Create your entity:
```java
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
```

4. Create your service:
```java
@ResourceService("user")
public class UserMessageService implements MessageService<UserDTO> {
    @Override
    public Message<UserDTO> process(Message<UserDTO> request) {
        // Your business logic here
    }
}
```

5. Start using the endpoints:
```bash
# Create a new resource
POST /api/process
{
    "header": {
        "resourceType": "user",
        "operation": "CREATE"
    },
    "body": {
        "data": {
            "name": "John Doe",
            "email": "john@example.com"
        }
    }
}

# Get resources with filtering and pagination
GET /api/resource/user?name=John&page=0&size=10
```

## 🎯 Advanced Usage

### Filtering
```java
// Advanced filtering example
GET /api/resource/user?filter=name:like:John,age:gt:25,status:in:ACTIVE,PENDING
```

### Caching
```java
@ResourceService("user")
@CacheConfig(cacheNames = "users")
public class UserMessageService implements MessageService<UserDTO> {
    @Cacheable(key = "#request.body.filterCriteria")
    public Message<UserDTO> process(Message<UserDTO> request) {
        // Cached response
    }
}
```

### Validation
```java
public class UserDTO {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    
    @Email
    private String email;
}
```

## 🤝 Contributing

We love your input! We want to make contributing to SpringMate as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

### Development Process
1. Fork the repo and create your branch from `main`
2. If you've added code that should be tested, add tests
3. If you've changed APIs, update the documentation
4. Ensure the test suite passes
5. Make sure your code lints
6. Issue that pull request!

### Code of Conduct
This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## 📝 License

SpringMate is open-source software licensed under the [Apache License 2.0](LICENSE).

## 🏗️ Project Structure
```
springmate-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/springmate/framework/
│   │   │       ├── core/
│   │   │       ├── data/
│   │   │       ├── service/
│   │   │       ├── config/
│   │   │       └── exception/
│   │   └── resources/
│   └── test/
├── docs/
├── examples/
└── pom.xml
```

## 🔍 Documentation

Full documentation is available at [https://springmate.io/docs](https://springmate.io/docs)

## 💡 Examples

Check out our [example projects](examples/) for complete implementation examples.

## 🤔 FAQ

### Q: How does caching work with dynamic filters?
A: SpringMate uses a composite cache key based on the filter criteria and pagination parameters...

### Q: Can I use custom repositories?
A: Yes! Simply extend our BaseRepository interface...

## 📞 Support

- 📧 Email: support@springmate.io
- 💬 Discord: [Join our community](https://discord.gg/springmate)
- 🐦 Twitter: [@SpringMateFrame](https://twitter.com/SpringMateFrame)

## ⭐ Show your support

Give a ⭐️ if this project helped you!

## 📊 Roadmap

- [ ] GraphQL Support
- [ ] WebFlux Integration
- [ ] OAuth2 Integration
- [ ] Metrics & Monitoring
- [ ] Cloud Native Features