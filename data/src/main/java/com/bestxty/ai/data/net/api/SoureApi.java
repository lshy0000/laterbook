package com.bestxty.ai.data.net.api;

import com.bestxty.ai.domain.bean.A;
import com.bestxty.ai.domain.bean.B;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.D;
import com.bestxty.ai.domain.bean.E;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.RankList;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.bean.Source;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lshy on 2018-4-28.
 */

public interface SoureApi {

    @GET("/cats/lv2/statistics")
    Observable<A> getFenleil();

    @GET("/cats/lv2")
    Observable<D> getFenleixl();

    @GET("/ranking/gender")
    Observable<B> getPaihangl();


    @GET("/ranking/{rankId}")
    Observable<RankList> getPaihangd(@Path("rankId") String rankId);

///book/by-categories?gender=male&type=hot&major=%E5%A5%87%E5%B9%BB&minor=&start=0&limit=20

    @GET("/book/by-categories")
    Observable<E> getList(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("start") int start, @Query("limit") int limit);

    //    http://api.zhuishushenqi.com/book/548d9c17eb0337ee6df738f5
    @GET("/book/{booId}")
    Observable<BookInfo> getBookInfo(@Path("booId") String booId);

    //    http://api.zhuishushenqi.com/atoc?view=summary&book=548d9c17eb0337ee6df738f5
    @GET("/atoc")
    Observable<List<Source>> getSource(@Query("view") String view, @Query("book") String bookId);

    //    http://api.zhuishushenqi.com/atoc/568fef99adb27bfb4b3a58dc?view=chapters
    @GET("/atoc/{bookId}")
    Observable<ChapterList> getChapters(@Path("bookId") String bookId, @Query("view") String chapters);


    //    http://api.zhuishushenqi.com/book/auto-complete?query=%E6%96%97%E7%BD%97
    @GET("/book/auto-complete")
    Observable<F> getSearchWord(@Query("query") String query);


//    http://api.zhuishushenqi.com/book/fuzzy-search?query=%E6%96%97%E7%BD%97

    @GET("book/fuzzy-search")
    Observable<SearchList> getSearchList(@Query("query") String query);
}
