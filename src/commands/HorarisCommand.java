package commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import services.menus;

/**
 * Created by jordi on 01/12/16.
 */
public class HorarisCommand extends BotCommand {
    private static final String LOGTAG = "HORARISCOMMAND";


    public HorarisCommand() {
        super("horaris", "Obtens els horaris de grups i professors");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();
        String userName = user.getFirstName();

        messageBuilder.append("Tria de qui vols veure horaris ").append(userName).append("\n");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(menus.MenuInlineButtonsHoraris());

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        answer.setReplyMarkup(markup);

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

}
