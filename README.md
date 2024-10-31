# Янулевич Виктория
## Структура проекта

- `org.example.model`: Содержит классы для представления организмов, таких как `Animal`, `Plant`, и `Ecosystem`.
- `org.example.repository`: Содержит классы для работы с данными экосистем, включая `EcosystemRepository` и `FileEcosystemRepository`.
- `org.example.service`: Содержит классы, управляющие логикой симуляции, прогнозирования и расчета популяций.
- `org.example.utils`: Содержит вспомогательные классы, такие как `Logger`, `OrganismFactory` и `InputValidator`.
- `org.example`: Содержит главный класс приложения `Main`, который управляет взаимодействием с пользователем.

## Основные классы и их описание

- **Main**: Точка входа в приложение. Управляет пользовательским интерфейсом и взаимодействием с другими сервисами.

- **Ecosystem**: Представляет экосистему с ее организмами, температурой, влажностью и доступностью воды.

- **Organism**: Абстрактный класс для всех организмов. Определяет общие свойства, такие как имя и популяция.

- **Animal**: Наследует от класса `Organism`. Представляет животных с добавлением скорости потребления пищи и типа животного (травоядное, плотоядное, всеядное).

- **Plant**: Наследует от класса `Organism`. Представляет растения с добавлением уровня потребности в воде.

- **Logger**: Отвечает за ведение логов, записывая события, такие как динамика популяций, взаимодействия между видами и климатические условия в файл.

- **PredictionService**: Обеспечивает функционал для прогнозирования изменений популяций на основе текущих климатических условий и доступных ресурсов.

- **EcosystemSimulationService**: Управляет процессом автоматической симуляции экосистемы, взаимодействуя с `PopulationCalculator` и `EcosystemService`.

## Вспомогательные классы

- **OrganismFactory**: Позволяет пользователю создавать организмы через консольный интерфейс, запрашивая необходимые параметры.

- **InputValidator**: Обеспечивает проверку вводимых пользователем данных, предотвращая ошибки при вводе.

- **OrganismDeserializer**: Обеспечивает десериализацию организмов из JSON формата, что позволяет загружать данные из файлов.
