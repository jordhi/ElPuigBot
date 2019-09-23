package updateshandlers;

import commands.BlogCommand;
import commands.HelpCommand;
import commands.HorarisCommand;
import commands.StartCommand;
import config.BotConfig;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import services.dataVars;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import services.Emoji;
import services.menus;

import java.io.File;

/**
 * Created by jordi on 01/12/16.
 */
public class CommandsHandler extends TelegramLongPollingCommandBot {
    private static final String LOGTAG = "COMMANDSHANDLER";
    public static final String backMenu = Emoji.ARROW_LEFTWARDS.toString();

    public CommandsHandler() {
        register(new HorarisCommand());
        register(new StartCommand());
        register(new BlogCommand());
        //register(new StopCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("La comanda '" + message.getText() + "' encara no està implementada o no és d'aquest bot. Aquí tens una ajuda " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });

    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

           if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Hola sóc un bot sense IA de moment només obeeixo ordres:\n" + message.getText()
                    + " i això no és cap ordre.\nEscriu /help");

                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String tria = callbackQuery.getData();
            //System.out.println(tria);

            SendMessage answer = new SendMessage();
            SendPhoto answerPhoto = new SendPhoto();
            SendDocument answerDoc = new SendDocument();
            answer.setChatId(callbackQuery.getMessage().getChatId());
            answerPhoto.setChatId(callbackQuery.getMessage().getChatId());
            answerDoc.setChatId(callbackQuery.getMessage().getChatId());

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            InlineKeyboardMarkup markup2 = null;



            if (tria.equals(backMenu)) {
                markup.setKeyboard(menus.MenuInlineButtonsHoraris());
                enviarResposta(callbackQuery, markup, "Tria de qui vols veure horaris:");
            } else {
                switch (tria) {
                    case "Professors":
                        markup.setKeyboard(menus.MenuInlineButtonsHorarisProfes());
                        enviarResposta(callbackQuery, markup, "Tria els horaris de:");
                        break;
                    case "Grups":
                        markup.setKeyboard(menus.MenuInlineButtonsHorariGrups());
                        enviarResposta(callbackQuery, markup, "Tria el grup:");
                        break;
                /*case backMenu :
                    markup.setKeyboard(menus.MenuInlineButtonsHoraris());
                    enviarResposta(callbackQuery, markup, "Tria de qui vols veure horaris:");
                    break;*/
                    case "Dilluns":
                        enviarResposta(answerDoc, dataVars.HPDilluns);
                        break;
                    case "Dimarts":
                        enviarResposta(answerDoc, dataVars.HPDimarts);
                        break;
                    case "Dimecres":
                        enviarResposta(answerDoc, dataVars.HPDimecres);
                        break;
                    case "Dijous":
                        enviarResposta(answerDoc, dataVars.HPDijous);
                        break;
                    case "Divendres":
                        enviarResposta(answerDoc, dataVars.HPDivendres);
                        break;
                    case "SMX1A":
                        enviarResposta(answerDoc, dataVars.HSMX1A);
                        break;
                    case "SMX1B":
                        enviarResposta(answerDoc, dataVars.HSMX1B);
                        break;
                    case "SMX1C":
                        enviarResposta(answerDoc, dataVars.HSMX1C);
                        break;
                    case "SMX2A":
                        enviarResposta(answerDoc, dataVars.HSMX2A);
                        break;
                    case "SMX2B":
                        enviarResposta(answerDoc, dataVars.HSMX2B);
                        break;
                    case "SMX2C":
                        enviarResposta(answerDoc, dataVars.HSMX2C);
                        break;
                    case "ASIX1":
                        enviarResposta(answerDoc, dataVars.HGS1ASIX);
                        break;
                    case "DAM1":
                        enviarResposta(answerDoc, dataVars.HGS1DAM);
                        break;
                    case "ASIX2":
                        enviarResposta(answerDoc, dataVars.HASIX2A);
                        break;
                    case "DAM2A":
                        enviarResposta(answerDoc, dataVars.HDAM2A);
                        break;
                    case "DAM2B":
                        enviarResposta(answerDoc, dataVars.HDAM2B);
                        break;
                }

            }

        }
    }

    @Override
    public String getBotUsername() {
        return BotConfig.USER_COMMAND;
    }

    @Override
    public String getBotToken() {
        return BotConfig.TOKEN_COMMAND;
    }

    /* Envia una foto d'una URL com a resposta */
    private void enviarResposta(SendPhoto resp, String url) {
        resp.setPhoto(url);
        try {
            sendPhoto(resp);
        } catch (TelegramApiException e2) {
            e2.printStackTrace();
        }
    }

    /* Envia un document desat en una ubicació com a resposta */
    private void enviarResposta(SendDocument resp, String url) {
        //resp.setPhoto(url);
        File f = new File(url);
        resp.setNewDocument(f);
        try {
            sendDocument(resp);
        } catch (TelegramApiException e2) {
            e2.printStackTrace();
        }
    }

    /* Envia un menu i un text nou com a resposta */
    private void enviarResposta2(SendMessage resp, InlineKeyboardMarkup rkm, String msg) {
        resp.setReplyMarkup(rkm);
        resp.setText(msg);
        try {
            sendMessage(resp);
        } catch (TelegramApiException e) {
            BotLogger.info(LOGTAG, e.getMessage());
        }

    }



    /* Canvia el menu i el text de resposta en el mateix menu del missatge anterior*/
    private void enviarResposta(CallbackQuery resp, InlineKeyboardMarkup rkm, String msg) {
        EditMessageText editMarkup = new EditMessageText();
        editMarkup.setChatId(resp.getMessage().getChatId().toString());
        editMarkup.setInlineMessageId(resp.getInlineMessageId());
        editMarkup.setText(msg);
        editMarkup.enableMarkdown(true);
        editMarkup.setMessageId(resp.getMessage().getMessageId());
        editMarkup.setReplyMarkup(rkm);
        try {
            editMessageText(editMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
