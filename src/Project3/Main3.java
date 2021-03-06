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
		System.out.println("초성우선-0 받침우선-1");
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
		if(a >= '가' && a <= '힣') {
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
					// TODO ㄳ 받침에서 획 추가 -> ㄱ받침+ㅈ
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
			case 'ㄱ':
				result.push('ㅋ');
				break;
			case 'ㄴ':
				result.push('ㄷ');
				break;
			case 'ㄷ':
				result.push('ㅌ');
				break;
			case 'ㅁ':
				result.push('ㅂ');
				break;
			case 'ㅂ':
				result.push('ㅍ');
				break;
			case 'ㅅ':
				result.push('ㅈ');
				break;
			case 'ㅇ':
				result.push('ㅎ');
				break;
			case 'ㅈ':
				result.push('ㅊ');
				break;
			case 'ㅊ':
				result.push('ㅅ');
				break;
			case 'ㅋ':
				result.push('ㄱ');
				break;
			case 'ㅌ':
				result.push('ㄴ');
				break;
			case 'ㅍ':
				result.push('ㅁ');
				break;
			case 'ㅎ':
				result.push('ㅇ');
				break;
			}
		}
	}

	static void addDouble(Stack<Character> result) {
		char a = result.pop();
		if(a >= '가' && a <= '힣') {
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
					result.push('ㅆ');
					break;
				case 5:
					jong = 4;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㅉ');
					break;
				case 7:
					jong = 0;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㄸ');
					break;
				case 9:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㄲ');
					break;
				case 11:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㅃ');
					break;
				case 12:
					jong = 8;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㅆ');
					break;
				case 17:
					jong = 0;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㅃ');
					break;
				case 18:
					jong = 17;
					a = getLetter(cho, jung, jong);
					result.push(a);
					result.push('ㅆ');
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
					result.push('ㅉ');
					break;
				}
			}
		} else {
			switch(a) {
			case 'ㄱ':
				result.push('ㄲ');
				break;
			case 'ㄲ':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, 'ㄱ', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('ㄱ');
				break;
			case 'ㄷ':
				result.push('ㄸ');
				break;
			case 'ㄸ':
				result.push('ㄷ');
				break;
			case 'ㅂ':
				result.push('ㅃ');
				break;
			case 'ㅃ':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, 'ㅂ', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('ㅂ');
				break;
			case 'ㅅ':
				result.push('ㅆ');
				break;
			case 'ㅆ':
				if(!result.isEmpty()) {
					char b = result.pop();
					int cho = getCho(b);
					int jung = getJung(b);
					int jong = getJong(b);
					int jong_new = addJong(jong, 'ㅅ', false);
					if(jong_new == -1)
						result.push(b);
					else {
						result.push(getLetter(cho, jung, jong_new));
						break;
					}
				}
				result.push('ㅅ');
				break;
			case 'ㅈ':
				result.push('ㅉ');
				break;
			case 'ㅉ':
				result.push('ㅈ');
				break;
			}
		}
	}

	static void addVowel(Stack<Character> result, char c) {
		char a = result.pop();
		if(a >= '가' && a <= '힣') {
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
			result.push('ㄱ');
			break;
		case '2':
			result.push('ㄴ');
			break;
		case 'q':
			result.push('ㄹ');
			break;
		case 'w':
			result.push('ㅁ');
			break;
		case 'a':
			result.push('ㅅ');
			break;
		case 's':
			result.push('ㅇ');
			break;
		}
	}

	static void addCon(Stack<Character> result, char c) {
		if(result.isEmpty()) {
			addSingle(result, c);
		} else {
			char a = result.pop();
			if(a >= '가' && a <= '힣') {
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
		case 'ㄱ':
			return 0;
		case 'ㄲ':
			return 1;
		case 'ㄴ':
			return 2;
		case 'ㄷ':
			return 3;
		case 'ㄸ':
			return 4;
		case 'ㄹ':
			return 5;
		case 'ㅁ':
			return 6;
		case 'ㅂ':
			return 7;
		case 'ㅃ':
			return 8;
		case 'ㅅ':
			return 9;
		case 'ㅆ':
			return 10;
		case 'ㅇ':
			return 11;
		case 'ㅈ':
			return 12;
		case 'ㅉ':
			return 13;
		case 'ㅊ':
			return 14;
		case 'ㅋ':
			return 15;
		case 'ㅌ':
			return 16;
		case 'ㅍ':
			return 17;
		case 'ㅎ':
			return 18;
		}
		return -1;
	}

	static char choToSingle(int cho) {
		switch(cho) {
		case 0:
			return 'ㄱ';
		case 1:
			return 'ㄲ';
		case 2:
			return 'ㄴ';
		case 3:
			return 'ㄷ';
		case 4:
			return 'ㄸ';
		case 5:
			return 'ㄹ';
		case 6:
			return 'ㅁ';
		case 7:
			return 'ㅂ';
		case 8:
			return 'ㅃ';
		case 9:
			return 'ㅅ';
		case 10:
			return 'ㅆ';
		case 11:
			return 'ㅇ';
		case 12:
			return 'ㅈ';
		case 13:
			return 'ㅉ';
		case 14:
			return 'ㅊ';
		case 15:
			return 'ㅋ';
		case 16:
			return 'ㅌ';
		case 17:
			return 'ㅍ';
		case 18:
			return 'ㅎ';
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
		if(a >= '가' && a <= '힣') {
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
			// TODO 초성우선인 경우 받침을 빼와야됨
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
