package com.gromit.gromitmod.commands;

import com.gromit.gromitmod.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.List;

public class CalculatorCommand implements ICommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final String wrongUsage = ClientUtils.getClientMessage("Wrong usage");

    @Override
    public String getCommandName() {
        return "math";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Simple calculator";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>(1);
        aliases.add("maths");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length == 0) {
            minecraft.thePlayer.addChatMessage(new ChatComponentText(wrongUsage));
            return;
        }
        StringBuilder expression = new StringBuilder();
        for (String string : strings) {
            expression.append(string);
        }

        Expression expr = new Expression(expression.toString());
        ClientUtils.addClientMessage(expr.getExpressionString() + " = " + expr.calculate());
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] strings, BlockPos blockPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        if (getCommandName().equalsIgnoreCase(o.getCommandName())) return 1;
        return 0;
    }
}
