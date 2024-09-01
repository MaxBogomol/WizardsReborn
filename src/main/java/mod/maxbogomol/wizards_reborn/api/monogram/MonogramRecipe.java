package mod.maxbogomol.wizards_reborn.api.monogram;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMonograms;
import net.minecraft.core.NonNullList;

import javax.annotation.Nonnull;

public class MonogramRecipe {
    public Monogram output;
    public NonNullList<Monogram> inputs;

    public MonogramRecipe(Monogram output, Monogram... inputs) {
        this.output = output;
        this.inputs = NonNullList.of(WizardsRebornMonograms.LUNAM, inputs);
    }

    public Monogram getOutput() {
        return output;
    }

    @Nonnull
    public NonNullList<Monogram> getInputs() {
        return inputs;
    }
}
