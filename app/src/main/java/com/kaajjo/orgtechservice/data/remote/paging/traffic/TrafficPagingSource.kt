package com.kaajjo.orgtechservice.data.remote.paging.traffic

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kaajjo.orgtechservice.data.remote.api.info.TrafficService
import com.kaajjo.orgtechservice.data.remote.dto.Traffic

class TrafficPagingSource(
    private val trafficService: TrafficService,
    private val key: String
) : PagingSource<Int, Traffic>() {
    // Max page number. Needed for pagination to know when to stop.
    // Because if requested page does not exist the API will return first page.
    private var totalPages = 1
    override fun getRefreshKey(state: PagingState<Int, Traffic>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Traffic> {
        return try {
            val page = params.key ?: 1

            val response = trafficService.getTrafficMonthly(key = key, page = page)
            totalPages = response.body()?.pagesCount ?: 1

            LoadResult.Page(
                data = response.body()?.traffic ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()?.traffic == null || response.body()?.page!! >= totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}