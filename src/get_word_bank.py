if __name__ == '__main__':
    pass
    # guess_words = []
    # answer_words = []
    #
    # with open('../wordle-allowed-guesses.txt', 'r') as file:
    #     for line in file.readlines():
    #         guess_words.append(line[:-1] if line[-1] == '\n' else line)
    #
    # with open('../wordle-answers-alphabetical.txt', 'r') as file:
    #     for line in file.readlines():
    #         answer_words.append(line[:-1] if line[-1] == '\n' else line)
    #
    # print(guess_words)
    # print(answer_words)
    #
    # word_bank = sorted(list(set(answer_words + guess_words)))
    #
    # with open('../wordle_word_bank.txt', 'w+') as new_file:
    #     for word in word_bank:
    #         new_file.write(word + '\n')

    # with open('../word_bank.txt', 'w+') as new_file:
    #     with open('../dictionary.txt', 'r') as file:
    #         for line in file.readlines():
    #             line = line if line[-1] != '\n' else line[:-1]
    #             line = line.lower()
    #             if len(line) == 5:
    #                 new_file.write(f'{line}\n')

    # with open('../char_frequency.csv', 'w+') as file:
    #     output = {}
    #     with open('../word_bank.txt', 'r') as bank_file:
    #         for line in bank_file.readlines():
    #             word = line if line[-1] != '\n' else line[:-1]
    #             for char in word:
    #                 if char in output.keys():
    #                     output[char] += 1
    #                 else:
    #                     output[char] = 1
    #
    #
    #
    #     output = {k: v for k, v in sorted(output.items(), key=lambda item: item[1], reverse=True)}
    #     for key, value in output.items():
    #         file.write(f'{key},{value}\n')
