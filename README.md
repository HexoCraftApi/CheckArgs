# CheckArgs
A java processor that force constructors to be declared as you want.

</br>

## How can I use it ?

Let's say you want to have all sub classes of NoArgsClass to declare a constructor without arguments, then just add @CheckArgs to your class declaration just as shown below.

```java
@CheckArgs
public abstract class NoArgsClass {
	public NoArgsClass() {
	}
}

```

If you want all sub classes of StringArgClass to declare a constructor with String class arguments :

```java
@CheckArgs( types = {String.class})
public abstract class StringArgClass {
	public StringArgClass(String s) {
	}
}

```

or with more arguments

```java
@CheckArgs( types = {String.class, long.class, String.class})
public abstract class MultipleArgClass {
	public StringArgClass(String s1, lonf l, String s2) {
	}
}

```

## Add CheckArgs to your project with maven

```maven
    <repositories>
        <repository>
            <id>hexosse-repo</id>
            <url>https://raw.github.com/hexosse/maven-repo/master/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- Dependency to CheckArgs annotation -->
        <dependency>
            <groupId>com.github.hexocraftapi.checkargs</groupId>
            <artifactId>annotation</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!-- Dependency to CheckArgs processor -->
        <dependency>
            <groupId>com.github.hexocraftapi.checkargs</groupId>
            <artifactId>processor</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
```    

