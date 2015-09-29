import java.util.ArrayList;
import java.util.List;

/*
 * Performance test over internal(parallel/sequential) and external iterations.
 * https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html
 * 
 * 
 * Parallel computing involves dividing a problem into subproblems, 
 * solving those problems simultaneously (in parallel, with each subproblem running in a separate thread),
 *  and then combining the results of the solutions to the subproblems. Java SE provides the fork/join framework, 
 *  which enables you to more easily implement parallel computing in your applications. However, with this framework, 
 *  you must specify how the problems are subdivided (partitioned). 
 *  With aggregate operations, the Java runtime performs this partitioning and combining of solutions for you.
 * 
 * Limit the parallelism that the ForkJoinPool offers you. You can do it yourself by supplying the -Djava.util.concurrent.ForkJoinPool.common.parallelism=1,
 *  so that the pool size is limited to one and no gain from parallelization
 *  
 *  @see ForkJoinPool
 *  https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
 *  
 *  ForkJoinPool, that pool creates a fixed number of threads (default: number of cores) and 
 *  will never create more threads (unless the application indicates a need for those by using managedBlock).
 *   *  http://stackoverflow.com/questions/10797568/what-determines-the-number-of-threads-a-java-forkjoinpool-creates
 *  
 */
public class IterationThroughStream {
	private static boolean found = false;
	private static List<Integer> smallListOfNumbers = null;
	public static void main(String[] args) throws InterruptedException {
		
		
		// TEST_1
		List<String> bigListOfStrings = new ArrayList<String>();
		for(Long i = 1l; i <= 1000000l; i++) {
			bigListOfStrings.add("Counter no: "+ i);
		}
	
		System.out.println("Test Start");
		System.out.println("-----------");
		long startExternalIteration = System.currentTimeMillis();
		externalIteration(bigListOfStrings);
		long endExternalIteration = System.currentTimeMillis();
		System.out.println("Time taken for externalIteration(bigListOfStrings) is :" + (endExternalIteration - startExternalIteration) + " , and the result found: "+ found);
	
		long startInternalIteration = System.currentTimeMillis();
		internalIteration(bigListOfStrings);
		long endInternalIteration = System.currentTimeMillis();
		System.out.println("Time taken for internalIteration(bigListOfStrings) is :" + (endInternalIteration - startInternalIteration) + " , and the result found: "+ found);
	
		
		
		
		
		// TEST_2
		smallListOfNumbers = new ArrayList<Integer>();
		for(int i = 1; i <= 10; i++) {
			smallListOfNumbers.add(i);
		}
		
		long startExternalIteration1 = System.currentTimeMillis();
		externalIterationOnSleep(smallListOfNumbers);
		long endExternalIteration1 = System.currentTimeMillis();
		System.out.println("Time taken for externalIterationOnSleep(smallListOfNumbers) is :" + (endExternalIteration1 - startExternalIteration1));
	
		long startInternalIteration1 = System.currentTimeMillis();
		internalIterationOnSleep(smallListOfNumbers);
		long endInternalIteration1 = System.currentTimeMillis();
		System.out.println("Time taken for internalIterationOnSleep(smallListOfNumbers) is :" + (endInternalIteration1 - startInternalIteration1));
		
		
		
		
		// TEST_3
		Thread t1 = new Thread(IterationThroughStream :: internalIterationOnThread);
		Thread t2 = new Thread(IterationThroughStream :: internalIterationOnThread);
		Thread t3 = new Thread(IterationThroughStream :: internalIterationOnThread);
		Thread t4 = new Thread(IterationThroughStream :: internalIterationOnThread);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		Thread.sleep(30000);
	}
	

	private static boolean externalIteration(List<String> bigListOfStrings) {
		found = false;
		for(String s : bigListOfStrings) {
			if(s.equals("Counter no: 1000000")) {
				found = true;
			}
		}
		return found;
	}

	private static boolean internalIteration(List<String> bigListOfStrings) {
		found = false;
		bigListOfStrings.parallelStream().forEach(
				(String s) -> { 
					if(s.equals("Counter no: 1000000")){  //Have a breakpoint to look how many threads are spawned.
						found = true;
					}
				
				}
			);
		return found;		
	}
	
	
	private static boolean externalIterationOnSleep(List<Integer> smallListOfNumbers) {
		found = false;
		for(Integer s : smallListOfNumbers) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return found;
	}

	private static boolean internalIterationOnSleep(List<Integer> smallListOfNumbers) {
		found = false;
		smallListOfNumbers.parallelStream().forEach( //Removing parallelStream() will behave as single threaded (sequential access).
				(Integer s) -> {
					try {
						Thread.sleep(100); //Have a breakpoint to look how many threads are spawned.
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			);
		return found;		
	}
	
	public static void internalIterationOnThread() {
		smallListOfNumbers.parallelStream().forEach(
				(Integer s) -> {
					try {
						/*
						 * DANGEROUS
						 * This will tell you that if all the 7 FJP(Fork join pool) worker threads are blocked for one single thread (e.g. t1), 
						 * then other normal three(t2 - t4) thread wont execute, will wait for FJP worker threads. 
						 */
						Thread.sleep(100); //Have a breakpoint here.
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			);
	}
}
