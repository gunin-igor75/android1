package ru.it_cron.android1.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.it_cron.android1.domain.model.app.AppItem
import ru.it_cron.android1.domain.model.app.AppItem.App
import ru.it_cron.android1.domain.model.app.AppItem.Header
import ru.it_cron.android1.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {

    private val services: MutableList<AppItem> = mutableListOf(
        Header("Услуги"),
        App("UX-тестирование"),
        App("Дизайн моб. приложения"),
        App("Дизайн веб-интерфейса"),
        App("Веб-разработка и интеграции"),
        App("Разработка моб. приложений"),
        App("Стратегия"),
        App("Креатив"),
        App("Аналитика"),
        App("Тестирование"),
        App("Другое")
    )

    private val budgets: MutableList<AppItem> = mutableListOf(
        Header("Бюджет"),
        App("< 500 тыс.р."),
        App("0.5 - 1 млн.р."),
        App("1 - 3 млн.р."),
        App("5 - 10 млн.р."),
        App("> 10 млн.р.")
    )

    private val areaActivity: MutableList<AppItem> = mutableListOf(
        Header("Откуда вы узнали о нас?"),
        App("Соцсети"),
        App("Рекомендации"),
        App("Работы"),
        App("Рейтинги"),
        App("Рассылка"),
        App("Реклама")
    )

    override fun getServices(): Flow<MutableList<AppItem>> = flow {
        emit(services)
    }

    override fun getBudgets(): Flow<MutableList<AppItem>> = flow {
        emit(budgets)
    }

    override fun getAreaActivity(): Flow<MutableList<AppItem>> = flow {
        emit(areaActivity)
    }
}