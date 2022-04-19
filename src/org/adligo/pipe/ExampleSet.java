package org.adligo.pipe;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.adligo.i_pipe.I_Pipe;

public class ExampleSet implements Function<Integer, String> {

	
  public static void main(String [] args) {
		new ExampleSet();
	}
  
  public ExampleSet() {
  	I_Pipe<Integer, Set<String>> p = new PipeCtx().newPipe(this)
  		.map((i) -> {
  			System.out.println("hey " + i + " wasn't filtered");
  			return i;
  		}).toSet();
  	
  	
		Optional<Set<String>> ssetOpt = p.get(List.of(1, 1, 2, 3, 4, 5, 55,  55, 55, 6, 7, 8, 99));
		for (String s: ssetOpt.get()) {
			System.out.println("hey you guys now in set form " + s);
		}
  }

	@Override
	public String apply(Integer s) {
		return "" + s;
	}
  
  
}
