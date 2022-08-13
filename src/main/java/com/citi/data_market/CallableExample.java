package com.citi.data_market;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample  implements Callable {
  @Override
  public Object call() throws Exception {
    Random generator = new Random();
    Integer randomNumber = generator.nextInt(5);
    Thread.sleep(randomNumber * 1000);
    return randomNumber;
  }

  @Test
  public void callabledTest(){
    ExecutorService executorService = Executors.newCachedThreadPool();
    CallableExample callableExample = new CallableExample();
    Future<Object> future = executorService.submit(callableExample);
    executorService.shutdown();
    try{
      System.out.println(future.get());
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
