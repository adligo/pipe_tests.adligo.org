package org.adligo.pipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.adligo.i_pipe.I_Consumer;

public class Example {

  public static void main(String[] args) {
    new Example();

//  	ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(50);
//  	Supplier<String> sup = () -> { return queue.poll(); };
//		Stream.generate(sup).map((s) -> {
//			System.out.println("hey at least it's deferred " + s);
//			return Integer.parseInt(s);
//		});
//		
//		queue.add("1");
  }

  public Example() {
    I_Consumer<String> p = new PipeCtx().newPipe(stepOne(), "one")
//				.filter((i) -> { 
//					System.out.println("in filter " + i);
//					switch(i) {
//					  case 123: return true;
//					  default: return false;
//					}
//				})
        .fork(stepTwo(), "two");
    ArrayList<String> list = new ArrayList<>();
    list.add("123");
    list.add("456");
    list.add("789");
    p.accept(list);
  }

  public Function<String, Integer> stepOne() {
    return (s) -> {
      System.out.println("Processing " + s);
      return Integer.parseInt(s);
    };
  }

  public Consumer<Integer> stepTwo() {
    // initialize these first
    I_Consumer<Integer> p123 = new PipeCtx().newPipe((Function<Integer,Integer>) (i) -> {
      return i++;
    }).then((i) -> {
      System.out.println("\t\tA it's now " + i);
    });

    I_Consumer<Integer> p456 = new PipeCtx().newPipe((Function<Integer,Integer>) (i) -> {
      return i * i++;
    }).then((i) -> {
      System.out.println("\t\tB it's now " + i);
    });

    // then call at run time
    return (i) -> {
      Integer ii = (Integer) i;
      switch (ii) {
      case 123:
        System.out.println("\tDid A for 123");
        p123.accept(ii);
        break;
      case 456:
        System.out.println("\tDid B for 456");
        p456.accept(ii);
        break;
      default:
        System.out.println("\tDid default");
      }
    };
  }
}
