/// Copyright 2022-2023 Andreas Kohler
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dev.schmarrn.lighty.fabric;

import dev.schmarrn.lighty.Lighty;
import dev.schmarrn.lighty.fabric.api.LightyModesRegistration;
import dev.schmarrn.lighty.event.Compute;
import dev.schmarrn.lighty.event.KeyBind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;

public class LightyFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(Compute::computeCache);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> Compute.render(context.frustum(), context.matrixStack(), context.projectionMatrix()));

        ClientTickEvents.END_CLIENT_TICK.register(KeyBind::handleKeyBind);

        Lighty.init();
        FabricLoader.getInstance().getEntrypoints("lightyModesRegistration", LightyModesRegistration.class).forEach(LightyModesRegistration::registerLightyModes);
        Lighty.postLoad();
    }
}