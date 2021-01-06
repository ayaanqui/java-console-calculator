package com.github.ayaanqui.javaconsolecalculator;

import java.util.Arrays;
import java.util.Scanner;
import com.github.ayaanqui.expressionresolver.Resolver;
import com.github.ayaanqui.expressionresolver.objects.Response;

public class App {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Resolver res = new Resolver();

        res.setFunction("median", params -> {
            Arrays.sort(params);
            int hLen = params.length / 2;
            return (params.length % 2 == 0) ? (params[hLen - 1] + params[hLen]) / 2 : params[hLen];
        });
        res.setFunction("len", params -> (double) params.length);
        res.setFunction("min", params -> {
            double min = params[0];
            for (double val : params)
                if (val < min)
                    min = val;
            return min;
        });
        res.setFunction("max", params -> {
            double max = params[0];
            for (double val : params)
                if (val > max)
                    max = val;
            return max;
        });

        while (true) {
            System.out.print(">> ");
            String input = keyboard.nextLine();

            if (input.equals("quit") || input.equals("exit"))
                break;

            Response output = res.setExpression(input).solveExpression();

            if (output.success)
                System.out.println(output.result);
            else {
                for (String msg : output.errors)
                    System.out.printf(" *%s", msg);
            }
            System.out.println();
        }
        keyboard.close();
    }
}
