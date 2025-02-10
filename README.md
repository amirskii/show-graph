# TapYou Android Assignment

## Выполненные фичи
- [x] главный экран с кнопкой "Поехали"
- [x] экран с таблицой точек и графиком
- [x] изменения масштаба пользователем
- [x] соединение точек не ломаной линией, а сглаженной
- [x] работа в портретной и ландшафтной ориентации экрана
- [x] сохранение изображения графика в файл

## Использованный стэк:
1. Architecture Components (Coroutine/Flow, ViewModel, Navigation)
2. MVVM, Koin for dependency injection
3. Kotlin written
4. SOLID, Clean Architecture

## Clean Architecture
Бизнес логика, слой данных, презентационный и фреймворк слои выделены в отдельные пакеты.

### Layers
- **Domain** - Бизнес логика, доменные модели и use cases
- **Data** - Network, дата модели(Дто) и кэширование.
- **Presentation** - слой UI and со своими моделями.
- **Framework** - Android framework. Сохрание графика в файл зависит от андрой СДК

## Unit-tests
Юнит тестами покрыты:
- ViewModels
- Use cases
- Repository


## Screenshots
[<img src="/screenshots/main.png" align="left" width="200" hspace="10" vspace="10">](/screenshots/main.png)
[<img src="/screenshots/chart.png" align="center" width="200" hspace="10" vspace="10">](/screenshots/chart.png)

### Unit tests
[<img src="/screenshots/tests.png" align="left" width="400" hspace="30" vspace="10">](/screenshots/tests.png)
