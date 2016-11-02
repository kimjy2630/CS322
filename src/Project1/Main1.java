package Project1;

import java.util.*;

import common.*;

public class Main1 {
	// public static String[] list_vowl = { "y", "u", "i", "o", "p", "h", "j", "k", "l", "n", "m" };

	public static int select;

	public static String[] list_consonant = { "ぁ", "あ", "い", "ぇ", "え", "ぉ", "け", "げ", "こ", "さ", "ざ", "し", "じ", "す", "ず", "せ", "ぜ", "そ", "ぞ" };
	public static String[] list_vowel = { "た", "だ", "ち", "ぢ", "っ", "つ", "づ", "て", "で", "と", "ど", "な", "に", "ぬ", "ね", "の", "は", "ば", "ぱ", "ひ", "び" };
	public static String[] list_consonant_jong = { "", "ぁ", "あ", "ぃ", "い", "ぅ", "う", "ぇ", "ぉ", "お", "か", "が", "き", "ぎ", "く", "ぐ", "け", "げ", "ご", "さ", "ざ", "し", "じ", "ず", "せ", "ぜ", "そ", "ぞ" };

	public static State state_init = new State("INIT");

	public static State state_cho = new State("CHOSEONG");

	public static State[] state_jung = new State[2];
	public static State[] state_jung_o1 = new State[2];
	public static State[] state_jung_o2 = new State[2];
	public static State[] state_jung_u1 = new State[2];
	public static State[] state_jung_u2 = new State[2];
	public static State[] state_jung_eu1 = new State[2];
	public static State[] state_jung_eu2 = new State[2];

	public static State[] state_jong = new State[7];
	public static State[] state_jong_g1 = new State[7];
	public static State[] state_jong_g2 = new State[7];
	public static State[] state_jong_n1 = new State[7];
	public static State[] state_jong_n2 = new State[7];
	public static State[] state_jong_r1 = new State[7];
	public static State[] state_jong_r2 = new State[7];
	public static State[] state_jong_b1 = new State[7];
	public static State[] state_jong_b2 = new State[7];

	public static Symbol[] sym_alphabet = new Symbol[26];
	public static Symbol[] sym_consonant = new Symbol[19];
	public static Symbol[] sym_vowel = new Symbol[21];

	public static Symbol sym_erase = new Symbol("<");

	public static HashMap<Character, Character> engToKor = new HashMap<>();

	public static Function<Pair<State, Symbol>, State> transFunc = new Function<>();
	public static Function<Pair<State, Symbol>, Action> outputFunc = new Function<>();

	public static int[][] list_vowel_change = { { 8, 0, 9 }, { 8, 1, 10 }, { 8, 20, 11 }, { 13, 4, 14 }, { 13, 5, 15 }, { 13, 20, 16 }, { 18, 20, 19 } };
	public static int[][] list_consonant_change = { { 1, 19, 3 }, { 4, 22, 5 }, { 4, 27, 6 }, { 8, 1, 9 }, { 8, 16, 10 }, { 8, 17, 11 }, { 8, 19, 12 }, { 8, 25, 13 }, { 8, 26, 14 }, { 8, 27, 15 }, { 17, 19, 18 } };

	public static int getNumCho(char c) {
		String str = Character.toString(c);
		for(int i = 0; i < list_consonant.length; ++i)
			if(list_consonant[i].equals(str))
				return i;
		return -1;
	}

	public static int getNumJung(char c) {
		String str = "" + c;
		for(int i = 0; i < list_vowel.length; ++i)
			if(list_vowel[i].equals(str))
				return i;
		return -1;
	}

	public static int getNumJong(char c) {
		String str = Character.toString(c);
		for(int i = 0; i < list_consonant_jong.length; ++i)
			if(list_consonant_jong[i].equals(str))
				return i;
		return -1;
	}

	public static void init() {
		Action action_put = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				stack.push(input.poll());
			}
		};
		Action action_add_jung = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char cho = stack.pop();
				char jung = input.poll();
				int num_cho = Main1.getNumCho(cho);
				int num_jung = Main1.getNumJung(jung);
				stack.push((char) (0xAC00 + ((num_cho * 21) + num_jung) * 28));
			}
		};
		Action action_change_jung = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				char jung_input = input.poll();
				int num_cho = (prev - 0xAC00) / (21 * 28);
				int num_jung_input = Main1.getNumJung(jung_input);

				int num_jung_old;
				if((num_jung_old = Main1.getNumJung(prev)) != -1) {
					for(int[] list : Main1.list_vowel_change) {
						if(list[0] == num_jung_old && list[1] == num_jung_input) {
							stack.push(Main1.list_vowel[list[2]].charAt(0));
							return;
						}
					}
				}

				num_jung_old = ((prev - 0xAC00) % (21 * 28)) / 28;
				for(int[] list : Main1.list_vowel_change) {
					if(list[0] == num_jung_old && list[1] == num_jung_input) {
						stack.push((char) (0xAC00 + ((num_cho * 21) + list[2]) * 28));
						break;
					}
				}
			}
		};
		Action action_add_jong = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				char jong = input.poll();
				int num_jong = Main1.getNumJong(jong);
				prev += num_jong;
				stack.push(prev);
			}
		};
		Action action_take_jong = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				char jung = input.poll();
				int num_jung = Main1.getNumJung(jung);
				int num_cho;
				if((num_cho = Main1.getNumCho(prev)) != -1) {
					stack.push((char) (0xAC00 + ((num_cho * 21) + num_jung) * 28));
				} else {
					int num_jong = (prev - 0xAC00) % 28;
					char cho = list_consonant_jong[num_jong].charAt(0);
					num_cho = Main1.getNumCho(cho);
					stack.push((char) (prev - num_jong));
					stack.push((char) (0xAC00 + (num_cho * 21 + num_jung) * 28));
				}
			}
		};
		Action action_take_jong2 = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				char jung = input.poll();
				int num_jung = Main1.getNumJung(jung);
				int num_cho;
				if((num_cho = Main1.getNumCho(prev)) != -1) {
					stack.push((char) (0xAC00 + ((num_cho * 21) + num_jung) * 28));
				} else {
					int num_jong = (prev - 0xAC00) % 28;
					for(int[] list : list_consonant_change) {
						if(list[2] == num_jong) {
							stack.push((char) (prev - num_jong + list[0]));
							int num_jong_new = list[1];
							char cho = Main1.list_consonant_jong[num_jong_new].charAt(0);
							num_cho = Main1.getNumCho(cho);
							stack.push((char) (0xAC00 + (num_cho * 21 + num_jung) * 28));
							break;
						}
					}
				}
			}
		};
		Action action_change_jong = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				char jong_input = input.poll();
				int num_jong_input = Main1.getNumJong(jong_input);
				int num_jong = (prev - 0xAC00) % 28;
				for(int[] list : list_consonant_change) {
					if(list[0] == num_jong && list[1] == num_jong_input) {
						stack.push((char) (prev - num_jong + list[2]));
						break;
					}
				}
			}
		};
		Action action_pop = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				stack.pop();
				input.poll();
			}
		};
		Action action_take_vowel = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				input.poll();
				int num_cho = (prev - 0xAC00) / (21 * 28);
				char cho = Main1.list_consonant[num_cho].charAt(0);
				stack.push(cho);
			}
		};
		Action action_take_vowel2 = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				input.poll();
				int num_jung_old;
				if((num_jung_old = Main1.getNumJong(prev)) != -1) {
					for(int[] list : Main1.list_vowel_change) {
						if(list[2] == num_jung_old) {
							stack.push(Main1.list_vowel[list[0]].charAt(0));
							return;
						}
					}
				} else {
					num_jung_old = ((prev - 0xAC00) % (21 * 28)) / 28;
					int num_cho = (prev - 0xAC00) / (21 * 28);
					for(int[] list : Main1.list_vowel_change) {
						if(list[2] == num_jung_old) {
							stack.push((char) (0xAC00 + ((num_cho * 21) + list[0]) * 28));
							break;
						}
					}
				}
			}
		};
		Action action_remove_jong = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				input.poll();
				int num_jong = (prev - 0xAC00) % 28;
				stack.push((char) (prev - num_jong));
			}
		};
		Action action_remove_jong2 = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char prev = stack.pop();
				input.poll();
				int num_jong = (prev - 0xAC00) % 28;
				for(int[] list : list_consonant_change) {
					if(list[2] == num_jong) {
						stack.push((char) (prev - num_jong + list[0]));
						break;
					}
				}
			}
		};
		Action action_change_jong_and_put = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char jong2 = stack.pop();
				char prev = stack.pop();
				char cho = input.poll();
				int num_jong1 = (prev - 0xAC00) % 28;
				int num_jong2 = Main1.getNumJong(jong2);
				for(int[] list : Main1.list_consonant_change) {
					if(list[0] == num_jong1 && list[1] == num_jong2) {
						stack.push((char) (prev - num_jong1 + list[2]));
						stack.push(cho);
						return;
					}
				}
				stack.push((char) (prev + num_jong2));
				stack.push(cho);
			}
		};
		Action action_add_jung_and_put = new Action() {
			@Override
			public void doSomething(Stack<Character> stack, Queue<Character> input) {
				char jong = stack.pop();
				char cho = input.poll();
				int num_jong;
				if((num_jong = Main1.getNumJong(jong)) != -1) {
					char prev = stack.pop();
					stack.push((char) (prev + num_jong));
				} else {
					stack.push(jong);
				}
				stack.push(cho);
			}
		};

		String[] str = { "", "alone" };
		for(int i = 0; i < 2; ++i) {
			state_jung[i] = new State("JUNGSEONG" + str[i]);
			state_jung_o1[i] = new State("JUNGSEONGで1" + str[i]);
			state_jung_o2[i] = new State("JUNGSEONGで2" + str[i]);
			state_jung_u1[i] = new State("JUNGSEONGぬ1" + str[i]);
			state_jung_u2[i] = new State("JUNGSEONGぬ2" + str[i]);
			state_jung_eu1[i] = new State("JUNGSEONGぱ1" + str[i]);
			state_jung_eu2[i] = new State("JUNGSEONGぱ2" + str[i]);
		}

		str = new String[] { "", "で1", "で2", "ぬ1", "ぬ2", "ぱ1", "ぱ2" };
		for(int i = 0; i < 7; ++i) {
			state_jong[i] = new State("JONGSEONG" + str[i]);
			state_jong_g1[i] = new State("JONGSEONG_ぁ1" + str[i]);
			state_jong_g2[i] = new State("JONGSEONG_ぁ2" + str[i]);
			state_jong_n1[i] = new State("JONGSEONG_い1" + str[i]);
			state_jong_n2[i] = new State("JONGSEONG_い2" + str[i]);
			state_jong_r1[i] = new State("JONGSEONG_ぉ1" + str[i]);
			state_jong_r2[i] = new State("JONGSEONG_ぉ2" + str[i]);
			state_jong_b1[i] = new State("JONGSEONG_げ1" + str[i]);
			state_jong_b2[i] = new State("JONGSEONG_げ2" + str[i]);
		}

		for(int i = 0; i < 26; ++i)
			sym_alphabet[i] = new Symbol((char) ('a' + i) + "");

		for(int i = 0; i < 19; ++i)
			sym_consonant[i] = new Symbol(list_consonant[i]);

		for(int i = 0; i < 21; ++i)
			sym_vowel[i] = new Symbol(list_vowel[i]);

		transFunc.addMapping(new Pair<State, Symbol>(state_init, sym_erase), state_init);
		outputFunc.addMapping(new Pair<State, Symbol>(state_init, sym_erase), action_pop);

		for(Symbol s : sym_consonant) {
			transFunc.addMapping(new Pair<State, Symbol>(state_init, s), state_cho);
			outputFunc.addMapping(new Pair<State, Symbol>(state_init, s), action_put);
		}
		transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_erase), state_init);
		outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_erase), action_pop);

		for(Symbol sym : sym_consonant) {
			transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym), state_cho);
			outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym), action_put);
		}

		int[] list_vowel_normal = { 0, 1, 2, 3, 4, 5, 6, 7, 12, 17, 20 };

		transFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[8]), state_jung_o1[1]);
		transFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[13]), state_jung_u1[1]);
		transFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[18]), state_jung_eu1[1]);
		outputFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[8]), action_put);
		outputFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[13]), action_put);
		outputFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[18]), action_put);

		for(int i : list_vowel_normal) {
			transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[i]), state_jung[0]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[i]), action_add_jung);

			transFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[i]), state_jung[1]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_init, sym_vowel[i]), action_put);
		}
		transFunc.addMapping(new Pair<State, Symbol>(state_jung[0], sym_erase), state_cho);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung[0], sym_erase), action_take_vowel);

		transFunc.addMapping(new Pair<State, Symbol>(state_jung[1], sym_erase), state_init);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung[1], sym_erase), action_pop);

		transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[8]), state_jung_o1[0]);
		transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[13]), state_jung_u1[0]);
		transFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[18]), state_jung_eu1[0]);
		outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[8]), action_add_jung);
		outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[13]), action_add_jung);
		outputFunc.addMapping(new Pair<State, Symbol>(state_cho, sym_vowel[18]), action_add_jung);

		transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[0], sym_erase), state_cho);
		transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[0], sym_erase), state_cho);
		transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[0], sym_erase), state_cho);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[0], sym_erase), action_take_vowel);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[0], sym_erase), action_take_vowel);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[0], sym_erase), action_take_vowel);

		transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[1], sym_erase), state_init);
		transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[1], sym_erase), state_init);
		transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[1], sym_erase), state_init);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[1], sym_erase), action_pop);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[1], sym_erase), action_pop);
		outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[1], sym_erase), action_pop);

		for(Symbol sym : sym_consonant) {
			transFunc.addMapping(new Pair<State, Symbol>(state_jung[1], sym), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[1], sym), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[1], sym), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[1], sym), state_cho);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung[1], sym), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[1], sym), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[1], sym), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[1], sym), action_put);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_o2[1], sym), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_u2[1], sym), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu2[1], sym), state_cho);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o2[1], sym), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u2[1], sym), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu2[1], sym), action_put);
		}

		for(int i = 0; i < 2; ++i) {
			for(int j = 0; j < 21; ++j) {
				if(j == 0 || j == 1 || j == 20) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), state_jung_o2[i]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), action_change_jung);
				}

				if(j == 4 || j == 5 || j == 20) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), state_jung_u2[i]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), action_change_jung);
				}

				if(j == 8) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), state_jung_o1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), state_jung_o1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), state_jung_o1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), state_jung_o1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), action_put);
				} else if(j == 13) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), state_jung_u1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), state_jung_u1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), state_jung_u1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), state_jung_u1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), action_put);
				} else if(j == 18) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), state_jung_eu1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), state_jung_eu1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), state_jung_eu1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), state_jung_eu1[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), action_put);
				} else {
					transFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), state_jung[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), state_jung[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), state_jung[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u1[i], sym_vowel[j]), action_put);

					transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), state_jung[1]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[j]), action_put);
				}
			}
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_o2[i], sym_erase), state_jung_o1[i]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_o2[i], sym_erase), action_take_vowel2);

			transFunc.addMapping(new Pair<State, Symbol>(state_jung_u2[i], sym_erase), state_jung_u1[i]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_u2[i], sym_erase), action_take_vowel2);

			transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[20]), state_jung_eu2[i]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu1[i], sym_vowel[20]), action_change_jung);
			transFunc.addMapping(new Pair<State, Symbol>(state_jung_eu2[i], sym_erase), state_jung_eu1[i]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jung_eu2[i], sym_erase), action_take_vowel2);
		}

		State[] list_state_jung = { state_jung[0], state_jung_o1[0], state_jung_o2[0], state_jung_u1[0], state_jung_u2[0], state_jung_eu1[0], state_jung_eu2[0] };

		Action a = (select == 0) ? action_put : action_add_jong;
		for(int j = 0; j < 7; ++j) {
			State st = list_state_jung[j];
			for(int i : new int[] { 1, 3, 6, 9, 10, 11, 12, 14, 15, 16, 17, 18 }) {
				transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[i]), state_jong[j]);
				outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[i]), a);
			}

			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[0]), state_jong_g1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[2]), state_jong_n1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[5]), state_jong_r1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[7]), state_jong_b1[j]);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[0]), a);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[2]), a);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[5]), a);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[7]), a);

			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[4]), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[8]), state_cho);
			transFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[13]), state_cho);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[4]), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[8]), action_put);
			outputFunc.addMapping(new Pair<State, Symbol>(st, sym_consonant[13]), action_put);

			for(State st_jong[] : new State[][] { state_jong, state_jong_g1, state_jong_n1, state_jong_r1, state_jong_b1 }) {
				transFunc.addMapping(new Pair<State, Symbol>(st_jong[j], sym_erase), st);
				outputFunc.addMapping(new Pair<State, Symbol>(st_jong[j], sym_erase), action_remove_jong);
			}
		}

		for(State st[] : new State[][] { state_jong, state_jong_g1, state_jong_n1, state_jong_r1, state_jong_b1 }) {
			for(int j = 0; j < 7; ++j) {
				for(int i : new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 12, 17, 20 }) {
					transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[i]), state_jung[0]);
					outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[i]), action_take_jong);
				}
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[8]), state_jung_o1[0]);
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[13]), state_jung_u1[0]);
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[18]), state_jung_eu1[0]);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[8]), action_take_jong);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[13]), action_take_jong);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[18]), action_take_jong);
			}
		}

		for(State st[] : new State[][] { state_jong_g2, state_jong_n2, state_jong_r2, state_jong_b2 }) {
			for(int j = 0; j < 7; ++j) {
				for(int i : new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 12, 17, 20 }) {
					transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[i]), state_jung[0]);
					outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[i]), action_take_jong2);
				}
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[8]), state_jung_o1[0]);
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[13]), state_jung_u1[0]);
				transFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[18]), state_jung_eu1[0]);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[8]), action_take_jong2);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[13]), action_take_jong2);
				outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym_vowel[18]), action_take_jong2);
			}
		}

		a = (select == 0) ? action_change_jong_and_put : action_put;
		for(State st[] : new State[][] { state_jong, state_jong_g2, state_jong_n2, state_jong_r2, state_jong_b2 }) {
			for(int j = 0; j < 7; ++j) {
				for(Symbol sym : sym_consonant) {
					transFunc.addMapping(new Pair<State, Symbol>(st[j], sym), state_cho);
					outputFunc.addMapping(new Pair<State, Symbol>(st[j], sym), a);
				}
			}
		}

		a = (select == 0) ? action_add_jung_and_put : action_change_jong;
		Action b = (select == 0) ? action_add_jung_and_put : action_put;
		for(int j = 0; j < 7; ++j) {
			for(int i = 0; i < 19; ++i)
				if(i == 9) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_g1[j], sym_consonant[i]), state_jong_g2[j]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_g1[j], sym_consonant[i]), a);
				} else {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_g1[j], sym_consonant[i]), state_cho);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_g1[j], sym_consonant[i]), b);
				}

			for(int i = 0; i < 19; ++i)
				if(i == 12 || i == 18) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_n1[j], sym_consonant[i]), state_jong_n2[j]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_n1[j], sym_consonant[i]), a);
				} else {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_n1[j], sym_consonant[i]), state_cho);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_n1[j], sym_consonant[i]), b);
				}

			for(int i = 0; i < 19; ++i)
				if(i == 0 || i == 6 || i == 7 || i == 9 || i == 16 || i == 17 || i == 18) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_r1[j], sym_consonant[i]), state_jong_r2[j]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_r1[j], sym_consonant[i]), a);
				} else {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_r1[j], sym_consonant[i]), state_cho);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_r1[j], sym_consonant[i]), b);
				}

			for(int i = 0; i < 19; ++i)
				if(i == 9) {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_b1[j], sym_consonant[i]), state_jong_b2[j]);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_b1[j], sym_consonant[i]), a);
				} else {
					transFunc.addMapping(new Pair<State, Symbol>(state_jong_b1[j], sym_consonant[i]), state_cho);
					outputFunc.addMapping(new Pair<State, Symbol>(state_jong_b1[j], sym_consonant[i]), b);
				}

			transFunc.addMapping(new Pair<State, Symbol>(state_jong_g2[j], sym_erase), state_jong_g1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(state_jong_n2[j], sym_erase), state_jong_n1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(state_jong_r2[j], sym_erase), state_jong_r1[j]);
			transFunc.addMapping(new Pair<State, Symbol>(state_jong_b2[j], sym_erase), state_jong_b1[j]);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jong_g2[j], sym_erase), action_remove_jong2);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jong_n2[j], sym_erase), action_remove_jong2);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jong_r2[j], sym_erase), action_remove_jong2);
			outputFunc.addMapping(new Pair<State, Symbol>(state_jong_b2[j], sym_erase), action_remove_jong2);
		}

		engToKor.put('<', '<');

		engToKor.put('q', 'げ');
		engToKor.put('Q', 'こ');
		engToKor.put('w', 'じ');
		engToKor.put('W', 'す');
		engToKor.put('e', 'ぇ');
		engToKor.put('E', 'え');
		engToKor.put('r', 'ぁ');
		engToKor.put('R', 'あ');
		engToKor.put('t', 'さ');
		engToKor.put('T', 'ざ');
		engToKor.put('y', 'に');
		engToKor.put('u', 'づ');
		engToKor.put('i', 'ち');
		engToKor.put('o', 'だ');
		engToKor.put('O', 'ぢ');
		engToKor.put('p', 'つ');
		engToKor.put('P', 'て');

		engToKor.put('a', 'け');
		engToKor.put('s', 'い');
		engToKor.put('d', 'し');
		engToKor.put('f', 'ぉ');
		engToKor.put('g', 'ぞ');
		engToKor.put('h', 'で');
		engToKor.put('j', 'っ');
		engToKor.put('k', 'た');
		engToKor.put('l', 'び');

		engToKor.put('z', 'せ');
		engToKor.put('x', 'ぜ');
		engToKor.put('c', 'ず');
		engToKor.put('v', 'そ');
		engToKor.put('b', 'ば');
		engToKor.put('n', 'ぬ');
		engToKor.put('m', 'ぱ');
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("段失酔識-0 閤徴酔識-1");
		select = sc.nextInt();
		sc.nextLine();

		init();

		while(true) {

			String input = sc.nextLine();
			if(input.equals("42"))
				return;
			// for(char c : input.toCharArray()) {
			// System.out.print(engToKor.get(c));
			// }

			State state = state_init;

			Queue<Action> que_action = new LinkedList<>();
			Queue<Character> que_input = new LinkedList<>();
			for(char c : input.toCharArray()) {
				if(c == '<' || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
					que_input.add(engToKor.get(c));
			}

			// for(int i = 0; i < input.length(); ++i) {
			for(char c : que_input) {
				Pair<State, Symbol> key = new Pair<State, Symbol>(state, new Symbol("" + c));
				// System.out.println(key);
				state = transFunc.getMapping().get(key);
				// Action action = outputFunc.getMapping().get(key);
				// System.out.println("ACT " + action == null);
				// que_action.offer(action);
				que_action.offer(outputFunc.getMapping().get(key));
				// System.out.println(state);
			}

			Stack<Character> result = new Stack<>();

			while(!que_action.isEmpty()) {
				Action action = que_action.poll();
				action.doSomething(result, que_input);
				// for(char c : result)
				// System.out.print(c);
				// System.out.println();
			}

			for(char c : result)
				System.out.print(c);
			System.out.println();
			// for(char c : result)
			// System.out.print((int) c);
		}

	}

	public static boolean isVowl(String s) {
		for(String ss : list_vowel)
			if(s.equals(ss))
				return true;
		return false;
	}
}
