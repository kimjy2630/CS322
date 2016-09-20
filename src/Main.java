import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while(!sc.nextLine().trim().equals("State")) {

		}
		String[] tmparr;

		tmparr = sc.nextLine().trim().split(",");
		State[] states = new State[tmparr.length];
		for(int i = 0; i < states.length; ++i) {
			states[i] = new State(tmparr[i]);
		}

		while(!sc.nextLine().trim().equals("Input symbol"))
			;
		tmparr = sc.nextLine().trim().split(",");
		Symbol[] symbols = new Symbol[tmparr.length];
		for(int i = 0; i < states.length; ++i) {
			symbols[i] = new Symbol(tmparr[i]);
		}
		while(!sc.nextLine().trim().equals("State transition function"))
			;
		String temp;
		TransitionFunction transFunc = new TransitionFunction();

		while(!(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(prevState, symbol, nextState);
		}
		String initState = sc.nextLine().trim();
		while(!sc.nextLine().trim().equals("Final state"))
			;
		String[] finalStates = sc.nextLine().trim().split(",");

		for(State s : states)
			System.out.print(s + " ");
		System.out.println();
		for(Symbol s : symbols)
			System.out.print(s + " ");
		System.out.println();
		for(String[] c : transFunc) {
			for(String s : c)
				System.out.print(s + " ");
			System.out.println();
		}

		System.out.println();
		System.out.println(initState);
		System.out.println();
		for(String s : finalStates)
			System.out.print(s + " ");

		while(sc.hasNextLine()) {
			temp = sc.nextLine();
			ArrayList<String> inputs = new ArrayList<>();
			while(!temp.isEmpty()) {
				for(Symbol s : symbols) {
					if(temp.startsWith(s)) {
						inputs.add(s);
						temp = temp.substring(s.length());
						break;
					}
				}
			}

			for(String s : inputs)
				System.out.print(s + " ");
			System.out.println();

			String present = initState;

			boolean flag = true;

			asdf: for(String s : inputs) {
				for(String[] c : transFunc) {
					if(c.length >= 3) {
						if(c[0].equals(present) && c[1].equals(s)) {
							present = c[2];
							continue asdf;
						}
					}
				}
				flag = false;
			}
			if(flag)
				System.out.println("��");
			else
				System.out.println("�ƴϿ�");
		}
	}
}
