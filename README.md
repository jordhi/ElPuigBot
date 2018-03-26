# ElPuig Bot
## Bot de telegram en proves per l'INS El Puig Castellar

Cal un fitxer `BotConfig.java` que contingui el TOKEN i USER del bot, seguiu l'enllaç [Com crear un bot](https://core.telegram.org/bots#creating-a-new-bot) per aprendre a generar-lo

Exemple de `BotConfig.java`
```java
public class BotConfig {
    public static final String TOKEN_COMMAND = string_token;
    public static final String USER_COMMAND = string_user_bot;
}
```
No funciona per a la Bot API superior a la 3.0
