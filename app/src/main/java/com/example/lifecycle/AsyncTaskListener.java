package com.example.lifecycle;

import java.net.URL;

public interface AsyncTaskListener <T>{
     public void onComplete(T results);
     public void launchTask(URL url);
}
