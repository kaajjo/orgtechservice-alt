# Оргтехсервис
Стороннее мобильное приложение для интернет-провайдера ОРГТЕХСЕРВИС.\
Приложение разработано лично мною. От оригинального приложения взято лишь API.\
P.S Надеюсь, что мне не дадут по голове за то, что я украл API через перехват траффика оригинального приожения 🥴
## Готовность проекта
-   ❌ Не начато
-   🟡 В прогрессе
-   ✅ Готово

### Из официального приложения

 Фича                          | Бета       | Стейбл      |  
------------------------------ | :----:     | :----:      |
Авторизация                    |  ✅        |  🟡        |      
Дашборд                        |  ✅        |  🟡        |
Тарифы                         |  ✅        |  🟡        |
Оплата                         |  ❌        |  ❌        |
Расход трафика                 |  🟡        |  ❌        |
Турбо режим                    |  ❌        |  ❌        |
Привязанные устройства         |  ✅        |  ✅        |
Логаут                         |  ✅        |  ✅        |
Новости                        |  ❌        |  ❌        |
Уведомление за день до оплаты  |  ❌        |  ❌        |
### 
 Фича                          | Бета       | Стейбл      |  
------------------------------ | :----:     | :----:      |
Вход по коду/отпечатку         |  ❌        |  ❌        |
## Стек
- [Material3](https://m3.material.io/) - дизайн и гайдлайны по интерфейсу
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - DI фреймворк
- [Compose Destinations](https://github.com/raamcosta/compose-destinations) - Навигация между экранами
- [Retrofit2](https://github.com/square/retrofit) и [OkHttp3](https://github.com/square/okhttp) - Запросы к API
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Постоянное хранилище данных
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Пагинация данных
- [Flow](https://kotlinlang.org/docs/flow.html) - Асинхронные потоки данных и реактив
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Асинхронность
- Прочите штуки, типа ViewModel и тому подобное
