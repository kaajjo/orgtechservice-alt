# Оргтехсервис
### NOTE: This is an alternative app for Russian internet provider - orgtechservice. Therefore, some commits and readme will be in Russian, sorry
### 🏗️ Work in progress. Most of the code is written to just work. We are not talking about any architecture or good code yet. Let's call it a prototype, I'm just testing it to see if it works 🏗️
Стороннее <b>НЕОФИЦИАЛЬНОЕ</b> мобильное приложение для интернет-провайдера оргтехсервис.

### Отличия от официального приложения (на данный момент)
- Открытый исходный код
- Material3 дизайн
- Быстрее, по крайней мере пока что
- Отсутствие различных ограничений:
  - История расхода трафика по месяцам, показывает всю историю, а не только последние 6 месяцев.
  - И прочие списки показываются в полном их объеме, а не ограничены первой страницей из API или как-то еще, как в официальном приложении.

## Готовность проекта
- ❌ Не начато
- 🟡 В процессе
- ✅ Готово

### Из официального приложения

 Фича                          | Бета       | Стейбл      |  
------------------------------ | :----:     | :----:      |
Авторизация                    |  ✅        |  ✅        |      
Дашборд                        |  ✅        |  🟡        |
Тарифы                         |  ✅        |  ✅        |
Оплата                         |  ❌        |  ❌        |
История платежей               |  ✅        |  ✅        |
Расход трафика                 |  🟡        |  ❌        |
Турбо режим                    |  ❌        |  ❌        |
Привязанные устройства         |  ✅        |  ✅        |
Логаут                         |  ✅        |  ✅        |
Новости                        |  ❌        |  ❌        |
Уведомление за день до оплаты  |  ❌        |  ❌        |
Мультиакк                      |  ❌        |  ❌        |
### Свои
 Фича                          | Бета       | Стейбл      |  
------------------------------ | :----:     | :----:      |
Вход по коду/отпечатку         |  ❌        |  ❌        |
####
P.S есть фичи, которые скорее всего не будут реализованны. Например, смена тарифа или добровольная блокировка. Причина проста, чтобы перехватить API запрос, мне нужно будет воспользоваться этими фичами, чего мне делать очень не хочется или не получится.
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
