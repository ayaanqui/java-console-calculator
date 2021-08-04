package com.github.ayaanqui.javaconsolecalculator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.objects.Response;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

public class App {

    private static List<Expression> history = new ArrayList<>(20);

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Resolver res = new Resolver();

        res.setFunction("median", params -> {
            Arrays.sort(params);
            int hLen = params.length / 2;
            return (params.length % 2 == 0) ? (params[hLen - 1] + params[hLen]) / 2 : params[hLen];
        });
        res.setFunction("len", params -> (double) params.length);
        res.setFunction("min", params -> Arrays.stream(params).reduce(params[0], (min, el) -> min = Math.min(min, el)));
        res.setFunction("max", params -> Arrays.stream(params).reduce(params[0], (max, el) -> max = Math.max(max, el)));
        res.setFunction("mod", params -> params[0] % params[1]);

        app: while (true) {
            System.out.print("> ");
            String input = keyboard.nextLine();

            switch (input) {
                case "quit":
                    break app;
                case "exit":
                    break app;
                case "history":
                    for (Expression e : history) {
                        if (e.success) {
                            System.out.printf("%s = %.20f\n", e.expression, e.result);
                        } else {
                            System.out.printf("%s = %s\n", e.expression, e.errors[0]);
                        }
                    }
                    break;
                default:
                    Response output = res.setExpression(input).solveExpression();

                    if (output.success) {
                        history.add(new Expression(input, output.result));
                        System.out.println(colorize(Double.toString(output.result), GREEN_TEXT()));
                    } else {
                        history.add(new Expression(input, output.result, output.errors));
                        for (String msg : output.errors)
                            System.out.println(colorize("* " + msg, WHITE_TEXT(), RED_BACK()));
                    }
                    break;
            }
            System.out.println();
        }
        keyboard.close();
    }
}
