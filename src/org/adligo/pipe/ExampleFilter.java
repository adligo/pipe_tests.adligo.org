package org.adligo.pipe;

import java.util.Optional;
import java.util.function.Function;

import org.adligo.i_pipe.I_Pipe;

public class ExampleFilter implements Function<String, Integer> {

  public static void main(String[] args) {
    new ExampleFilter();
  }

  public ExampleFilter() {
    I_Pipe<String, Integer> p = new PipeCtx().newPipe(this).filter((i) -> {
      System.out.println("in filter with " + i);
      switch (i) {
      case 123:
        return true;
      default:
        return false;
      }
    }).then((i) -> {
      System.out.println("hey " + i + " wasn't filtered");
      return i;
    });
    Optional<Integer> i = p.get("123");
    System.out.println("and a PipeFuture can return, ie " + i.get() + "\n\n");
    p.supply("456");
  }

  @Override
  public Integer apply(String s) {
    System.out.println("ahmm " + s);
    return Integer.parseInt(s);
  }

}
