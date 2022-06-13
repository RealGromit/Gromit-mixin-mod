package com.gromit.gromitmod.commands;

import com.gromit.gromitmod.utils.ClientUtils;
import com.gromit.gromitmod.utils.MathUtils;
import com.gromit.gromitmod.utils.primitivewrapper.GromitPosDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CannonCalculateCommand implements ICommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final String wrongUsage = ClientUtils.getClientMessage("<usage> /calc <barrel/custom> <power amount> <ticks> <power/position1> <position2>");

    @Override
    public String getCommandName() {
        return "calc";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "Calculate cannon range";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>(1);
        aliases.add("calculate");
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) {
        if (strings.length == 0) {
            minecraft.thePlayer.addChatMessage(new ChatComponentText(wrongUsage));
            return;
        }

        if (strings.length == 9 && strings[0].equalsIgnoreCase("custom")) {
            double power = ClientUtils.isNumeric(strings[1]);
            double ticks = ClientUtils.isNumeric(strings[2]);
            double powerX = ClientUtils.isNumeric(strings[3]);
            double powerY = ClientUtils.isNumeric(strings[4]);
            double powerZ = ClientUtils.isNumeric(strings[5]);
            double projectileX = ClientUtils.isNumeric(strings[6]);
            double projectileY = ClientUtils.isNumeric(strings[7]);
            double projectileZ = ClientUtils.isNumeric(strings[8]);
            if (power == -1 || ticks == -1 || powerX == -1 || powerY == -1 || powerZ == -1 ||projectileX == -1 || projectileY == -1 || projectileZ == -1) {
                minecraft.thePlayer.addChatMessage(new ChatComponentText(wrongUsage));
                return;
            }

            GromitPosDouble range = MathUtils.getRange(new GromitPosDouble(powerX, powerY, powerZ), new GromitPosDouble(projectileX, projectileY, projectileZ), (int) power, (int) ticks);
            if (range == null) ClientUtils.addClientMessage("Could not find range");
            else {
                ClientUtils.addClientMessage("-----------------------------------");
                ClientUtils.addClientMessage("Float: " + range.getX());
                ClientUtils.addClientMessage("Y adjust: " + range.getY());
                ClientUtils.addClientMessage("Range: " + range.getZ());
                ClientUtils.addClientMessage("-----------------------------------");
            }
        } else minecraft.thePlayer.addChatMessage(new ChatComponentText(wrongUsage));
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
