package com.bestxty.ai.data.net.api;

import com.bestxty.ai.domain.bean.Details;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lshy on 2018-4-29.
 */

public interface DetailsApi {
    @GET("/chapter/{link}")
    Observable<Details> getChapterDetails(@Path("link") String link);

}
