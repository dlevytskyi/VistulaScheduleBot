package com.vistula.schedule;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot{

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try{
            telegramBotsApi.registerBot(bot);
            System.out.println(Schedule.getSchedule());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if(message != null && message.hasText()){
            if (message.getText().equals("/schedule")){
                try {
                    sendMsg(message, Schedule.getSchedule());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                sendMsg(message, "How are you?");
            }
        }
    }



    @Override
    public String getBotUsername() {
        return "Vistual Schedule Bot";
    }

    @Override
    public String getBotToken() {
        return "541322309:AAEbhB9n89bBVB_c_Bqf1Wq0C80jTxSSgaE";
    }

    private void sendMsg(Message message, String s){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
