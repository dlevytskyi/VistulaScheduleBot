package com.vistula.schedule;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try{
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String group = "";

        if(message != null && message.hasText()){
            if(message.getText().equals("/start")){
                sendMsg(message, "Welcome to Vistula Schedule Bot. \n" +
                        "To get your schedule type: \n/schedule");
            }
            else if (message.getText().equals("/schedule")){
                selectYourGroup(message);
            }
            else if (message.getText().equals("group 1")){
                group = "group 1";
                try {
                    sendMsg(message, "Please wait a few seconds, \nwe are getting your schedule");
                    sendDoc(message,Schedule.convertToImg(Schedule.getSchedule(group)), "Your Schedule");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (message.getText().equals("group 2")){
                group = "group 2";
                try {
                    sendMsg(message, "Please wait a few seconds, \nwe are getting your schedule");
                    sendDoc(message,Schedule.convertToImg(Schedule.getSchedule(group)), "Your Schedule");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                sendMsg(message, message.getText());
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

    private void sendDoc (Message message, java.io.File save, String caption) throws TelegramApiException{

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId().toString());
        sendDocument.setNewDocument(save);
        sendDocument.setCaption(caption);
        sendDocument(sendDocument);
    }

    private void sendImg (Message message, java.io.File file, String caption) throws  TelegramApiException{

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setNewPhoto(file);
        sendPhoto.setCaption(caption);
        sendPhoto(sendPhoto);
    }

    public void selectYourGroup(Message message){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("group 1");
        row.add("group 2");
        keyboard.add(row);

        replyKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = new SendMessage().setChatId(message.getChatId().toString()).setText("Select your group");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
