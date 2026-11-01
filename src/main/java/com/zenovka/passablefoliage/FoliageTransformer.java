package com.zenovka.passablefoliage;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class FoliageTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null || !"net.minecraft.block.Block".equals(transformedName)) return bytes;
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        boolean patched = false;
        for (MethodNode method : node.methods) {
            if (!method.name.equals("addCollisionBoxesToList") && !method.name.equals("func_149743_a") && !method.name.equals("a")) continue;
            // World, x, y, z, AxisAlignedBB, List, Entity
            if (!method.desc.endsWith("Lnet/minecraft/entity/Entity;)V") &&
                !method.desc.endsWith("Lsa;)V")) continue;
            InsnList prefix = new InsnList();
            LabelNode continueLabel = new LabelNode();
            prefix.add(new VarInsnNode(Opcodes.ALOAD, 0));
            prefix.add(new VarInsnNode(Opcodes.ALOAD, 1));
            prefix.add(new VarInsnNode(Opcodes.ILOAD, 2));
            prefix.add(new VarInsnNode(Opcodes.ILOAD, 3));
            prefix.add(new VarInsnNode(Opcodes.ILOAD, 4));
            prefix.add(new VarInsnNode(Opcodes.ALOAD, 7));
            prefix.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                "com/zenovka/passablefoliage/FoliageHooks", "omitCollision",
                "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIILnet/minecraft/entity/Entity;)Z", false));
            prefix.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));
            prefix.add(new InsnNode(Opcodes.RETURN));
            prefix.add(continueLabel);
            method.instructions.insert(prefix);
            patched = true;
        }
        if (!patched) { System.err.println("[PassableFoliage] WARNING: Block collision method not found; leaving class unchanged"); return bytes; }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}
