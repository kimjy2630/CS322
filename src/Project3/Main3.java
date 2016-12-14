package Project3;

import java.io.*;
import java.util.*;

import common.*;

public class Main3 {
	static int select;

	public static void main(String[] args) {
		// TODO
		Scanner sc = null;
		try {
			sc = new Scanner(new File("dfa_proj3.txt"));
		} catch(FileNotFoundException e) {
			System.err.println("No input: dfa_proj3.txt");
			System.exit(0);
		}

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa_proj3.txt");
			System.exit(0);
		}

		String[] tmparr;

		tmparr = sc.nextLine().trim().split(",");

		ArrayList<State> states = new ArrayList<>();
		for(String s : tmparr)
			states.add(new State(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Input symbol"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa_proj3.txt");
			System.exit(0);
		}

		tmparr = sc.nextLine().trim().split(",");
		ArrayList<Symbol> symbols = new ArrayList<>();
		for(String s : tmparr)
			symbols.add(new Symbol(s));

		while(sc.hasNextLine() && !sc.nextLine().trim().equals("State transition function"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa_proj3.txt");
			System.exit(0);
		}

		String temp;
		Function<Pair<State, Symbol>, State> transFunc = new Function<>();

		while(sc.hasNextLine() && !(temp = sc.nextLine().trim()).equals("Initial state")) {
			tmparr = temp.split(",");
			State prevState = new State(tmparr[0]);
			Symbol symbol = new Symbol(tmparr[1]);
			State nextState = new State(tmparr[2]);
			transFunc.addMapping(new Pair<>(prevState, symbol), nextState);
		}
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa_proj3.txt");
			System.exit(0);
		}

		State initState = new State(sc.nextLine().trim());
		while(sc.hasNextLine() && !sc.nextLine().trim().equals("Final state"))
			;
		if(!sc.hasNextLine()) {
			System.err.println("Bad format in dfa_proj3.txt");
			System.exit(0);
		}

		ArrayList<State> finalStates = new ArrayList<>();
		tmparr = sc.nextLine().trim().split(",");
		for(String s : tmparr)
			finalStates.add(new State(s));

		// TODO
		sc.close();

		sc = new Scanner(System.in);
		System.out.println("ÃÊ¼º¿ì¼±-0 ¹ÞÄ§¿ì¼±-1");
		select = sc.nextInt();
		sc.nextLine();

		// init();

		while(true) {

			String input = sc.nextLine();
			if(input.equals("f"))
				return;
			// for(char c : input.toCharArray()) {
			// System.out.print(engToKor.get(c));
			// }

			State state = initState;

			// Queue<Action> que_action = new LinkedList<>();
			Queue<Character> que_input = new LinkedList<>();
			Stack<Character> result = new Stack<>();
			for(char c : input.toCharArray()) {
				que_input.add(c);
			}

			// for(int i = 0; i < input.length(); ++i) {
			for(char c : que_input) {
				Pair<State, Symbol> key = new Pair<State, Symbol>(state, new Symbol("" + c));
				// System.out.println(key);
				state = transFunc.getMapping().get(key);
				// Action action = outputFunc.getMapping().get(key);
				// System.out.println("ACT " + action == null);
				// que_action.offer(action);
				outputFunc(result, c);
				// System.out.println(state);
			}

			for(char c : result)
				System.out.print(c);
			System.out.println();
			// for(char c : result)
			// System.out.print((int) c);
		}
	}

	static void outputFunc(Stack<Character> result, char c) {
		if(c == 'z')
			addLine(result);
		else if(c == 'c')
			addDouble(result);
		else if(c == 'r') {
			delete(result);
		} else if(isCon(c)) {
			addCon(result, c);
		} else if(isVowel(c)) {
			addVowel(result, c);
		}
	}

	static void addLine(Stack<Character> result) {
		char a = result.pop();
		if(a >= '°¡' && a <= 'ÆR') {
			int cho = getCho(a);
			int jung = getJung(a);
			int jong = getJong(a);
			if(jong == 0) {
				switch(jung) {
				case 0:
					jung = 2;
					break;
				case 2:
					jung = 0;
					break;
				case 4:
					jung = 6;
					break;
				case 6:
					jung = 4;
					break;
				case 8:
					jung = 12;
					break;
				case 12:
					jung = 8;
					break;
				case 13:
					jung = 17;
					break;
				case 17:
					jung = 13;
					break;
				}
				a = getLetter(cho, jung, jong);
				result.push(a);
			} else {
				// TODO
				switch(jong) {
				case 1:
					jong = 24;
					break;
				case 3:
					// TODO ¤£ ¹ÞÄ§¿¡¼­ È¹ Ãß°¡ -> ¤¡¹ÞÄ§+¤¸
					break;
				case 4:
					jong = 7;
					break;
				case 5:
					// TODO
					break;
				case 6:
					// TODO
					break;
				case 7:
					jong = 25;
					break;
				case 9:
					// TODO
					break;
				case 10:
					// TODO
					break;
				case 11:
					// TODO
					break;
				case 12:
					// TODO
					break;
				case 13:
					// TODO
					break;
				case 14:
					// TODO
					break;
				case 15:
					// TODO
					break;
				case 16:
					jong = 17;
					break;
				case 17:
					jong = 26;
					break;
				case 18:
					// TODO
					break;
				case 19:
					jong = 22;
					break;
				case 21:
					jong = 27;
					break;
				case 22:
					jong = 23;
					break;
				case 23:
					jong = 19;
					break;
				case 24:
					jong = 1;
					break;
				case 25:
					jong = 4;
					break;
				case 26:
					jong = 16;
					break;
				case 27:
					jong = 21;
					break;
				}
				a = getLetter(cho, jung, jong);
				result.push(a);
			}
		} else {
			switch(a) {
			case '¤¡':
				result.push('¤»');
				break;
			case '¤¤':
				result.push('¤§');
				break;
			case '¤§':
				result.push('¤¼');
				break;
			case '¤±':
				result.push('¤²');
				break;
			case '¤²':
				result.push('¤½');
				break;
			case '¤µ':
				result.push('¤¸');
				break;
			case '¤·':
				result.push('¤¾');
				break;
			case '¤¸':
				result.push('¤º');
				break;
			case '¤º':
				result.push('¤µ');
				break;
			case '¤»':
				result.push('¤¡');
				break;
			case '¤¼':
				result.push('¤¤');
				break;
			case '¤½':
				result.push('¤±');
				break;
			case '¤¾':
				result.push('¤·');
				break;
			}
		}
	}

	static void addDouble(Stack<Character> result) {
		char a = result.pop();
		if(a >= '°¡' && a <= 'ÆR') {
			int cho = getCho(a);
			int jung = getJung(a);
			int jong = getJong(a);
			if(jong != 0) {
				switch(jong) {
				case 1:
					jong = 2;
					a = getLetter(cho, jung, jong);
					result.push(a);
					break;
				case 2:
					jong = 1;
					a = getLetter(cho, jung, jong);
					result.push(a);
					break;
				case 3:
					jong = 1;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¶');
					break;
				case 5:
					jong = 4;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¹');
					break;
				case 7:
					jong = 0;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¨');
					break;
				case 9:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¢');
					break;
				case 11:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤³');
					break;
				case 12:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¶');
					break;
				case 17:
					jong = 0;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤³');
					break;
				case 18:
					jong = 17;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¶');
					break;
				case 19:
					jong = 20;
					a = getLetter(cho, jung, jong);
					result.push(a);
					break;
				case 20:
					jong = 19;
					a = getLetter(cho, jung, jong);
					result.push(a);
					break;
				case 22:
					jong = 0;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('¤¹');
					break;
				}
			}
		} else {
			switch(a) {
			case '¤¡':
				result.push('¤¢');
				break;
			case '¤¢':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, '¤¡', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('¤¡');
				break;
			case '¤§':
				result.push('¤¨');
				break;
			case '¤¨':
				result.push('¤§');
				break;
			case '¤²':
				result.push('¤³');
				break;
			case '¤³':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, '¤²', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('¤²');
				break;
			case '¤µ':
				result.push('¤¶');
				break;
			case '¤¶':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, '¤µ', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('¤µ');
				break;
			case '¤¸':
				result.push('¤¹');
				break;
			case '¤¹':
				result.push('¤¸');
				break;
			}
		}
	}

	static void addVowel(Stack<Character> result, char c) {
		char a = result.pop();
		if(a >= '°¡' && a <= 'ÆR') {
			int cho = getCho(a);
			int jung = getJung(a);
			int jong = getJong(a);
			if(jong == 0) {
				switch(c) {
				case '3':
					switch(jung) {
					case 8:
					case 13:
						++jung;
						break;
					}
					break;
				case 'd':
					switch(jung) {
					case 0:
					case 2:
					case 4:
					case 6:
					case 9:
					case 14:
					case 18:
						++jung;
						break;
					case 8:
					case 13:
						jung = jung + 3;
						break;
					}
				}
				a = getLetter(cho, jung, jong);
				result.push(a);
			} else {
				int cho_new = jongToCho(jong);
				switch(jong) {
				case 3:
					jong = 1;
					break;
				case 5:
				case 6:
					jong = 4;
					break;
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
					jong = 8;
					break;
				case 18:
					jong = 17;
					break;
				default:
					jong = 0;
				}
				a = getLetter(cho, jung, jong);
				result.push(a);
				a = getLetter(cho_new, inputToJung(c), 0);
				result.push(a);
			}
		} else {
			a = getLetter(singleToCho(a), inputToJung(c), 0);
			result.push(a);
		}
	}

	static void addSingle(Stack<Character> result, char c) {
		switch(c) {
		case '1':
			result.push('¤¡');
			break;
		case '2':
			result.push('¤¤');
			break;
		case 'q':
			result.push('¤©');
			break;
		case 'w':
			result.push('¤±');
			break;
		case 'a':
			result.push('¤µ');
			break;
		case 's':
			result.push('¤·');
			break;
		}
	}

	static void addCon(Stack<Character> result, char c) {
		if(result.isEmpty()) {
			addSingle(result, c);
		} else {
			char a = result.pop();
			if(a >= '°¡' && a <= 'ÆR') {
				if(select == 0) {
					result.push(a);
					addSingle(result, c);
				} else {
					int cho = getCho(a);
					int jung = getJung(a);
					int jong = getJong(a);
					int jong_new = addJong(jong, c, true);
					if(jong_new == -1) {
						a = getLetter(cho, jung, jong);
						result.push(a);
						addSingle(result, c);
					} else {
						a = getLetter(cho, jung, jong_new);
						result.push(a);
					}
				}
			} else {
				char b = result.pop();
				int cho = getCho(b);
				int jung = getJung(b);
				int jong = getJong(b);
				int jong_new = addJong(jong, a, false);
				if(jong_new == -1) {
					b = getLetter(cho, jung, jong);
					result.push(b);
					result.push(a);
				} else {
					b = getLetter(cho, jung, jong_new);
					result.push(b);
				}
				addSingle(result, c);
			}
		}
	}

	static boolean isCon(char c) {
		return c == '1' || c == '2' || c == 'q' || c == 'w' || c == 'a' || c == 's';
	}

	static boolean isVowel(char c) {
		return !isCon(c) && c != 'z' && c != 'c';
	}

	static int getCho(char c) {
		return (c - 0xAC00) / (21 * 28);
	}

	static int getJung(char c) {
		return ((c - 0xAC00) % (21 * 28)) / 28;
	}

	static int getJong(char c) {
		return (c - 0xAC00) % 28;
	}

	static char getLetter(int cho, int jung, int jong) {
		return (char) (0xAC00 + ((cho * 21) + jung) * 28 + jong);
	}

	static int choToJong(int cho) {
		switch(cho) {
		case 0:
			return 1;
		case 1:
			return 2;
		case 2:
			return 4;
		case 3:
			return 7;
		case 5:
			return 8;
		case 6:
			return 16;
		case 7:
			return 17;
		case 9:
			return 19;
		case 10:
			return 20;
		case 11:
			return 21;
		case 12:
			return 22;
		case 14:
			return 23;
		case 15:
			return 24;
		case 16:
			return 25;
		case 17:
			return 26;
		case 18:
			return 27;
		}
		return -1;
	}

	static int jongToCho(int jong) {
		switch(jong) {
		case 1:
		case 9:
			return 0;
		case 2:
			return 1;
		case 4:
			return 2;
		case 7:
			return 3;
		case 8:
			return 5;
		case 10:
		case 16:
			return 6;
		case 11:
		case 17:
		case 18:
			return 7;
		case 3:
		case 12:
		case 19:
			return 9;
		case 20:
			return 10;
		case 21:
			return 11;
		case 5:
		case 22:
			return 12;
		case 23:
			return 14;
		case 24:
			return 15;
		case 13:
		case 25:
			return 16;
		case 14:
		case 26:
			return 17;
		case 6:
		case 15:
		case 27:
			return 18;
		}
		return -1;
	}

	static int singleToCho(char c) {
		switch(c) {
		case '¤¡':
			return 0;
		case '¤¢':
			return 1;
		case '¤¤':
			return 2;
		case '¤§':
			return 3;
		case '¤¨':
			return 4;
		case '¤©':
			return 5;
		case '¤±':
			return 6;
		case '¤²':
			return 7;
		case '¤³':
			return 8;
		case '¤µ':
			return 9;
		case '¤¶':
			return 10;
		case '¤·':
			return 11;
		case '¤¸':
			return 12;
		case '¤¹':
			return 13;
		case '¤º':
			return 14;
		case '¤»':
			return 15;
		case '¤¼':
			return 16;
		case '¤½':
			return 17;
		case '¤¾':
			return 18;
		}
		return -1;
	}

	static char choToSingle(int cho) {
		switch(cho) {
		case 0:
			return '¤¡';
		case 1:
			return '¤¢';
		case 2:
			return '¤¤';
		case 3:
			return '¤§';
		case 4:
			return '¤¨';
		case 5:
			return '¤©';
		case 6:
			return '¤±';
		case 7:
			return '¤²';
		case 8:
			return '¤³';
		case 9:
			return '¤µ';
		case 10:
			return '¤¶';
		case 11:
			return '¤·';
		case 12:
			return '¤¸';
		case 13:
			return '¤¹';
		case 14:
			return '¤º';
		case 15:
			return '¤»';
		case 16:
			return '¤¼';
		case 17:
			return '¤½';
		case 18:
			return '¤¾';
		}
		return (char) -1;
	}

	static int inputToCho(char c) {
		switch(c) {
		case '1':
			return 0;
		case '2':
			return 2;
		case 'q':
			return 5;
		case 'w':
			return 6;
		case 'a':
			return 9;
		case 's':
			return 11;
		}
		return -1;
	}

	static int inputToJung(char c) {
		switch(c) {
		case '3':
			return 0;
		case 'e':
			return 8;
		case 'd':
			return 20;
		case 'x':
			return 18;
		}
		return -1;
	}

	static int inputToJong(char c) {
		switch(c) {
		case '1':
			return 1;
		case '2':
			return 4;
		case 'q':
			return 8;
		case 'w':
			return 16;
		case 'a':
			return 19;
		case 's':
			return 21;
		}
		return -1;
	}

	/**
	 * Return jong code if jong changes. Otherwise, return -1. In this case, c becomes cho.
	 * 
	 * @param c
	 * @return
	 */
	static int addJong(int jong, char c, boolean input) {
		int cho_new;
		if(input)
			cho_new = inputToCho(c);
		else
			cho_new = singleToCho(c);
		switch(jong) {
		case 0:
			if(input)
				return inputToJong(c);
			else
				return choToJong(singleToCho(c));
		case 1:
			if(cho_new == 9)
				return 3;
			break;
		case 4:
			if(cho_new == 12)
				return 5;
			else if(cho_new == 18)
				return 6;
			break;
		case 8:
			if(cho_new == 0)
				return 9;
			else if(cho_new == 6)
				return 10;
			else if(cho_new == 7)
				return 10;
			else if(cho_new == 9)
				return 11;
			else if(cho_new == 16)
				return 12;
			else if(cho_new == 17)
				return 13;
			else if(cho_new == 18)
				return 14;
			break;
		case 17:
			if(cho_new == 9)
				return jong = 18;
			break;
		}
		return -1;
	}

	static void delete(Stack<Character> result) {
		char a = result.pop();
		if(a >= '°¡' && a <= 'ÆR') {
			int cho = getCho(a);
			int jung = getJung(a);
			int jong = getJong(a);
			if(jong == 0) {
				switch(jung) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 9:
				case 10:
				case 14:
				case 15:
				case 19:
					--jung;
					break;
				case 11:
				case 16:
					jung = jung - 3;
					break;
				default:
					jung = -1;
				}
				if(jung != -1) {
					a = getLetter(cho, jung, jong);
					result.push(a);
				} else {
					result.push(choToSingle(cho));
				}
			} else {
				switch(jong) {
				case 3:
					jong = 1;
					break;
				case 5:
				case 6:
					jong = 4;
					break;
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
				case 15:
					jong = 8;
					break;
				case 18:
					jong = 17;
					break;
				default:
					jong = 0;
				}
				a = getLetter(cho, jung, jong);
				result.push(a);
			}
		} else {
			// TODO ÃÊ¼º¿ì¼±ÀÎ °æ¿ì ¹ÞÄ§À» »©¿Í¾ßµÊ
			if(select == 0) {
				if(!result.isEmpty()) {
					a = result.pop();
					int cho = getCho(a);
					int jung = getJung(a);
					int jong = getJong(a);
				}
			}
		}
	}
}
