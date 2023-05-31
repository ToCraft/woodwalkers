package tocraft.walkers.screen.widget;

import tocraft.walkers.api.variant.ShapeType;
import tocraft.walkers.network.impl.SwapPackets;
import tocraft.walkers.screen.WalkersScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class EntityWidget<T extends LivingEntity> extends PressableWidget {

    private final ShapeType<T> type;
    private final T entity;
    private final int size;
    private final WalkersScreen parent;

    public EntityWidget(float x, float y, float width, float height, ShapeType<T> type, T entity, WalkersScreen parent) {
        super((int) x, (int) y, (int) width, (int) height, Text.of("")); // int x, int y, int width, int height, message
        this.type = type;
        this.entity = entity;
        size = (int) (25 * (1 / (Math.max(entity.getHeight(), entity.getWidth()))));
        entity.setGlowing(true);
        this.parent = parent;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Update current Walkers
        SwapPackets.sendSwapRequest(type, true);
        parent.disableAll();
        // close active screen handler
        parent.close();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        // Some entities (namely Aether mobs) crash when rendered in a GUI.
        // Unsure as to the cause, but this try/catch should prevent the game from entirely dipping out.
        try {
            InventoryScreen.drawEntity(matrices, this.getX() + this.getWidth() / 2, (int) (this.getY() + this.getHeight() * .75f), size, -10, -10, entity);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void onPress() {

    }

    @Override
public void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
