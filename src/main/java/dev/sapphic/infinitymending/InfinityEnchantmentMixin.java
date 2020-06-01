/*
 * Copyright (C) 2020 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.sapphic.infinitymending;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.enchantment.MendingEnchantment;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * A mixin for {@link InfinityEnchantment} to alter the enchantment compatibility
 * logic, negating the short-circuit against {@link MendingEnchantment} instances
 *
 * @author Chloe Dawn
 */
@Mixin(InfinityEnchantment.class)
abstract class InfinityEnchantmentMixin extends Enchantment {
    private InfinityEnchantmentMixin() {
        super(null, null, null);
    }

    /**
     * Ensures that {@code enchantment instanceof MendingEnchantment} is always false
     * to have the target method always delegate to the super implementation
     * <pre>{@code
     * return (false) ? false : super.differs(enchantment);
     * }</pre>
     *
     * @param ref The enchantment being compared for compatibility
     * @param cls The type of {@link MendingEnchantment}
     * @return Always false
     */
    @Deterministic
    @Contract(value = "_, _ -> false")
    @ModifyConstant(method = "differs", require = 1, allow = 1,
        constant = @Constant(classValue = MendingEnchantment.class))
    private static boolean instanceOf(final @Nullable Object ref, final @NonNull Class<?> cls) {
        return false;
    }
}
