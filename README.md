# AdvancementExecutor

## Descripción

AdvancementExecutor es un plugin de Minecraft que permite la ejecución de acciones basadas en el progreso de los logros (advancements) en el juego. Permite a los administradores del servidor personalizar la experiencia de juego al reaccionar a los logros de los jugadores.

## Características Principales

-   Gestión de comandos para interactuar con el plugin.
-   Gestión de configuración para personalizar el comportamiento del plugin.
-   Registro de eventos para responder a los logros de los jugadores.
-   Mensajes de inicio y apagado del plugin.

## Requisitos

-   Minecraft Server (compatible con Bukkit/Spigot/Paper)
-   Java Development Kit (JDK)

## Instalación

1.  Coloca el archivo .jar del plugin en la carpeta `plugins` de tu servidor de Minecraft.
2.  Inicia o reinicia el servidor.

## Uso

-   `/advancementexecutor` o `/ae`: Comando principal.
    -   `reset <player> <facil|medio|intermedio|dificil|muydificil>`: Reinicia el contador de logros de un jugador para una dificultad específica.
    -   `add <player> <facil|medio|intermedio|dificil|muydificil> <points>`: Añade puntos al contador de logros de un jugador para una dificultad específica.
    -   `subtract <player> <facil|medio|intermedio|dificil|muydificil> <points>`: Resta puntos del contador de logros de un jugador para una dificultad específica.

## Dependencias

-   Bukkit API
-   Lombok

## Licencia MIT

Este proyecto está licenciado bajo la Licencia MIT. Para obtener más información, visite el archivo [LICENSE](LICENSE) en el repositorio.
