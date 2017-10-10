package commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by jordi on 15/12/16.
 */
public class BlogCommand  extends BotCommand {
    private static final String LOGTAG = "BLOGCOMMAND";


    public BlogCommand() {
        super("blog", "Entrades del blog");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();
        String userName = user.getFirstName();

        messageBuilder.append("http://blog.elpuig.xeill.net/?p=1765");

        //InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        //markup.setKeyboard(menus.MenuInlineButtonsHoraris());

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        //answer.setReplyMarkup(markup);



        try {

            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }

    }
}
