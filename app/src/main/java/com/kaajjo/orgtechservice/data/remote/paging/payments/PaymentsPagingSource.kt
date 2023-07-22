package com.kaajjo.orgtechservice.data.remote.paging.payments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kaajjo.orgtechservice.data.remote.api.payment.PaymentService
import com.kaajjo.orgtechservice.data.remote.dto.PaymentHistoryItem

class PaymentsPagingSource(
    private val paymentService: PaymentService,
    private val key: String
) : PagingSource<Int, PaymentHistoryItem>() {
    private var totalPages = 1

    override fun getRefreshKey(state: PagingState<Int, PaymentHistoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PaymentHistoryItem> {
        return try {
            val page = params.key ?: 1

            val response = paymentService.getPaymentHistory(key, page)
            totalPages = response.pagesCount

            LoadResult.Page(
                data = response.payments ,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}