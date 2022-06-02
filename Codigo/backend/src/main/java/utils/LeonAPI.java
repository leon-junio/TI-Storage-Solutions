/*
 @author Leon Junio Martins Ferreira
 @version 1.8.1 build 3
 @date 03/05/2022
 Essa � uma classe de fun��es importantes para ferramentas de uso em sistemas backend Java
 Uso pessoal e garantido por licen�a de uso pessoal para sistemas autorais
 ler license.txt
 */
package utils;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author junio
 */
public class LeonAPI {

    public static void escrever(String text, String file) throws IOException {
        new File("dir/bin/" + file).delete();
        File f = new File("dir/bin/" + file);
        BufferedWriter out;
        out = new BufferedWriter(new FileWriter(f));
        out.write(text);
        out.close();
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static JsonObject stringToJson(String txt) {
        JsonObject model = Json.createObjectBuilder()
                .add("response", txt)
                .build();
        return model;
    }

    // METODOS PARA CONVERTER HORAS E DATAS
    public static String dinheiroConverter(float num) {
        try {
            df.setRoundingMode(RoundingMode.HALF_UP);
            String text = "R$ ";
            text += df.format(num);
            return text;
        } catch (Exception e) {
            System.out.println("Erro na convers�o de pre�o");
            return null;
        }
    }

    public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        try {
            String resposta, jss = "";
            int linha = 0;
            while ((resposta = buffereReader.readLine()) != null) {
                if (linha == 13 && jss.charAt(jss.length() - 1) == ',') {
                    int i = jss.length() - 1;
                    while (i > 0) {
                        if (jss.charAt(i) == '}') {
                            break;
                        }
                        if (jss.charAt(i) == ' ') {
                            jss = jss.substring(0, i - 1);
                        }
                        if (jss.charAt(i) == ',') {
                            jss = jss.substring(0, i) + " ";
                            break;
                        }
                        i--;
                    }
                    break;
                }
                jss += resposta;
                linha++;
            }
            if (jss.equals("[]") || jss.equals("{  \"erro\": true}")) {
                return "404";
            }
            if (jss.charAt(0) == '{') {
                return jss;
            } else {
                return jss.substring(3, jss.length() - 1);
            }
        } catch (Exception e) {
            System.out.println("Erro interno do secure date API");
            return null;
        }
    }

    public static boolean errors(Component c, String msg) {
        try {
            JOptionPane.showMessageDialog(c, msg, "Erro!", JOptionPane.ERROR_MESSAGE);
            return true;
        } catch (Exception e) {
            System.out.println("Erro na msg de erro");
            return false;
        }
    }

    public static boolean info(Component c, String msg) {
        try {
            JOptionPane.showMessageDialog(c, msg, "Informa��o", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            System.out.println("Erro na msg de info");
            return false;
        }
    }

    public static int pergunta(Component cp, String msg, int op) {
        switch (op) {
            case 1:
                return JOptionPane.showConfirmDialog(cp, msg, "Decis�o:", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            case 2:
                return JOptionPane.showConfirmDialog(cp, msg, "Sair:", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            case 3:
                return JOptionPane.showConfirmDialog(cp, msg, "Escolha:", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
            default:
                return -1;
        }
    }

    public static int exclusao(Component cp, String msg) {
        return JOptionPane.showConfirmDialog(cp, msg, "Exclus�o", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    private MaskFormatter mask;

    public MaskFormatter formatCpf() {
        try {
            mask = new MaskFormatter("###.###.###-##");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    public MaskFormatter formatRg() {
        try {
            mask = new MaskFormatter("##.###.###-#");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    public MaskFormatter formatCnpj() {
        try {
            mask = new MaskFormatter("##.###.###/0001-##");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    private static String cpx = "";

    public static String gerarCpf(String cpf) {
        cpx = "";
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        for (int i = 0; i < cpf.length(); i++) {
            cpx += cpf.charAt(i);
            if (i == 2) {
                cpx += ".";
            }
            if (i == 5) {
                cpx += ".";
            }
            if (i == 8) {
                cpx += "-";
            }
        }
        return cpx;
    }

    public MaskFormatter formatTelefone() {
        try {
            mask = new MaskFormatter("(##)####-####");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    public MaskFormatter formatCelular() {
        try {
            mask = new MaskFormatter("(##)#####-####");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    public MaskFormatter formatCep() {
        try {
            mask = new MaskFormatter("#####-###");
            return mask;
        } catch (ParseException ex) {
            return null;
        }
    }

    private static final SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");

    public static String formatHoras(java.sql.Date d) {
        return sfd.format(d);
    }
    

    public static Date formatDate(String str) throws ParseException {
        Date dt = sfd.parse(str);
        return dt;
    }
    

    /**
     * Fun��o que retorna valor em String de uma sequ�ncia de caracteres usados
     * para filtros de type no teclado
     *
     * @return uma lista de caracteres de A ate Z e 1 a 9
     */
    public static String chartb() {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321������������������������";
    }

    public static String geradorTab(int som) {
        String tab = "";
        for (int j = 0; j < som; j++) {
            tab += " ";
        }
        return tab;
    }

    private static char dig10, dig11;
    private static int sm, i, r, num, peso;
    private static Calendar cal, td;

    /**
     * Fun��o que retorna a diferen�a se uma data � antes ou depois do dia atual
     *
     * @param d data selecionada
     * @return verdade se for antes do dia de hoje, false se for depois do dia
     *         de hoje
     */
    public static boolean compareDate(java.util.Date d) {
        cal = Calendar.getInstance();
        td = Calendar.getInstance();
        cal.setTime(d);
        td.setTime(new Date());
        return cal.before(td);
    }

    /**
     * Met�do para avaliar se um CPF � v�lido
     *
     * @param cpf String contendo o CPF
     * @return Verdadeiro ou falso de acordo com a verifica��o
     * @throws Exception Caso ocorra algum erro
     */
    public static boolean verificarCpf(String cpf) throws Exception {
        dig10 = 0;
        dig11 = 0;
        sm = 0;
        i = 0;
        r = 0;
        num = 0;
        peso = 0;
        if (cpf.contains(".")) {
            cpf = cpf.replace(".", "");
        }
        if (cpf.contains("-")) {
            cpf = cpf.replace("-", "");
        }
        if (cpf.trim().length() != 11) {
            return false;
        }
        if (cpf.equals("00000000000")
                || cpf.equals("11111111111")
                || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777")
                || cpf.equals("88888888888") || cpf.equals("99999999999")
                || (cpf.length() != 11)) {
            return (false);
        }

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static boolean validaCNH(String cnh) {
        char char1 = cnh.charAt(0);
        if (cnh.replaceAll("\\D+", "").length() != 11
                || String.format("%0" + 11 + "d", 0).replace('0', char1).equals(cnh)) {
            return false;
        }
        long v = 0, j = 9;
        for (int i = 0; i < 9; ++i, --j) {
            v += ((cnh.charAt(i) - 48) * j);
        }

        long dsc = 0, vl1 = v % 11;

        if (vl1 >= 10) {
            vl1 = 0;
            dsc = 2;
        }
        v = 0;
        j = 1;
        for (int i = 0; i < 9; ++i, ++j) {
            v += ((cnh.charAt(i) - 48) * j);
        }
        long x = v % 11;
        long vl2 = (x >= 10) ? 0 : x - dsc;
        return (String.valueOf(vl1) + String.valueOf(vl2)).equals(cnh.substring(cnh.length() - 2));
    }

    private static final GregorianCalendar agora = new GregorianCalendar();
    private static final GregorianCalendar nascimento = new GregorianCalendar();
    private static int anoNasc = 0, mesNasc = 0, diaNasc = 0;
    private static int ano = 0, mes = 0, dia = 0, idade = 0;

    /**
     * Fun��o respons�vel por gerar uma idade baseado em uma data
     *
     * @param data data para retornar idade
     * @return idade em inteiro
     */
    public static int gerarIdade(java.sql.Date data) {
        idade = 0;
        if (data != null) {
            nascimento.setTime(data);
            ano = agora.get(Calendar.YEAR);
            mes = agora.get(Calendar.MONTH) + 1;
            dia = agora.get(Calendar.DAY_OF_MONTH);
            anoNasc = nascimento.get(Calendar.YEAR);
            mesNasc = nascimento.get(Calendar.MONTH) + 1;
            diaNasc = nascimento.get(Calendar.DAY_OF_MONTH);
            idade = ano - anoNasc;
            if (mes < mesNasc) {
                idade--;
            } else {
                if (dia < diaNasc) {
                    idade--;
                }
            }
            if (idade < 0) {
                idade = 0;
            }
        }
        return idade;
    }

    // conversor de timesstamp para locadate
    public static LocalDateTime converTimestamp(java.sql.Timestamp tms) throws Exception {
        return tms.toLocalDateTime();
    }

    private static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Formatador de dttimer para horario local
    public static String formatDtTimer(LocalDateTime lcd) throws Exception {
        return lcd.format(dtFormatter);
    }

    // Formatador de cart�o SUS
    public static String formatSus(String card) {
        String resp = "";
        for (int j = 1; j <= card.length(); j++) {
            if (j % 4 == 0) {
                if (j != card.length()) {
                    resp += "-";
                }
                resp += card.charAt(j - 1);
            } else {
                resp += card.charAt(j - 1);
            }
        }
        return resp;
    }

    // verificar se uma string � inteiramente numeros
    public static boolean isNumber(String str) {
        boolean resp = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                resp = false;
                i = str.length();
            }
        }
        return resp;
    }

    public static String generateToken(int n) {
        int lowerLimit = 97;
        int upperLimit = 122;
        Random random = new Random();
        StringBuffer r = new StringBuffer(n);
        for (int i = 0; i < n; i++) {
            int nextRandomChar = lowerLimit
                    + (int) (random.nextFloat()
                            * (upperLimit - lowerLimit + 1));
            r.append((char) nextRandomChar);
        }
        return r.toString();
    }
}
