% Генерация последовательности Голда
function gold_sequence_analysis
    % Исходные значения для регистров x и y
    x = [0 0 0 0 1]; % x = 5 в двоичном формате
    y = [1 1 0 1 0]; % y = x + 7 в двоичном формате
    sequenceLength = 31; % Длина последовательности Голда

    % Генерация исходной последовательности Голда
    goldSequence = generateGoldSequence(x, y, sequenceLength);

    % Отображение исходной последовательности
    fprintf('Исходная последовательность:\n');
    fprintf('%d ', goldSequence);
    fprintf('\n\n');


    % Вычисление и вывод автокорреляции
    disp('Таблица автокорреляции:');

    autocorrelationValues = calculateAutocorrelation(goldSequence);




    % Построение графика автокорреляции
    figure;
    plot(0:sequenceLength - 1, autocorrelationValues, '-o');
    title('Функция автокорреляции');
    xlabel('Задержка (lag)');
    ylabel('Автокорреляция');
    grid on;

    % Генерация новой последовательности Голда
    newGoldSequence = generateNewGoldSequence(sequenceLength);
    fprintf('\nНовая последовательность Голда:\n');
    fprintf('%d ', newGoldSequence);
    fprintf('\n\n');

    % Вычисление и вывод взаимной корреляции
    disp('Значение взаимной корреляции:');
    calculateAndPrintCrossCorrelation(goldSequence, newGoldSequence);


end


% Функция генерации последовательности Голда
function sequence = generateGoldSequence(x, y, length)
    sequence = zeros(1, length);
    for i = 1:length
        bitX = mod(x(5) + x(3), 2);
        bitY = mod(y(5) + y(4) + y(3) + y(1), 2);
        sequence(i) = mod(x(5) + y(5), 2);
        x = shiftRegister(x, bitX);
        y = shiftRegister(y, bitY);
    end
end

% Функция сдвига регистра
function register = shiftRegister(register, newBit)
    register = [register(2:end), newBit];
end
% Функция вычисления автокорреляции
function autocorrelationValues = calculateAutocorrelation(sequence)
    sequenceLength = length(sequence);
    autocorrelationValues = zeros(1, sequenceLength);

    for shift = 0:sequenceLength - 1
        shiftedSequence = circshift(sequence, [0, shift]);
        autocorrValue = sum(sequence == shiftedSequence) / sequenceLength * 2 - 1;
        autocorrelationValues(shift + 1) = autocorrValue;

        % Вывод строки автокорреляции
        fprintf('%2d ', shift);
        fprintf('%d ', shiftedSequence);
        fprintf('%.2f\n', autocorrValue);
    end
end

% Функция для генерации новой последовательности Голда с x+1 и y-5
function newSequence = generateNewGoldSequence(length)
    x = [0 0 0 1 1]; % x = x + 1
    y = [1 0 0 0 1]; % y = y - 5
    newSequence = generateGoldSequence(x, y, length);
end

% Функция для вычисления и вывода взаимной корреляции
function calculateAndPrintCrossCorrelation(seq1, seq2)
    length = numel(seq1);
    crossCorrelation = sum(2 * (seq1 == seq2) - 1) / length;
    fprintf('Взаимная корреляция: %.2f\n', crossCorrelation);
end
