package com.bestxty.ai.domain.interactor;

import com.bestxty.ai.domain.executor.BehindSchedulerProvider;
import com.bestxty.ai.domain.executor.PostExecutionThread;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by lshy on 2018-3-5.
 */

public abstract class UseCase2 {
    private final BehindSchedulerProvider behindSchedulerProvider;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    UseCase2(BehindSchedulerProvider behindSchedulerProvider, PostExecutionThread postExecutionThread) {
        this.behindSchedulerProvider = behindSchedulerProvider;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }


    public <T> void execute(DisposableObserver<T> observer, Observable<T> observable) {
//        Observable.just(observable).
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
        DisposableObserver<T> c = observable.observeOn(behindSchedulerProvider.getScheduler()).subscribeOn(behindSchedulerProvider.getScheduler()).observeOn(postExecutionThread.getScheduler()).subscribeWith(observer);
        addDisposable(c);
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        if (disposable == null) return;
        disposables.add(disposable);
    }
}
