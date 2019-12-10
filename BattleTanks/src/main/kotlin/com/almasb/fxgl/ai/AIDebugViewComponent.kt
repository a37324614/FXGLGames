package com.almasb.fxgl.ai

import com.almasb.fxgl.ai.goap.GoapAction
import com.almasb.fxgl.ai.goap.GoapComponent
import com.almasb.fxgl.ai.goap.GoapListener
import com.almasb.fxgl.ai.goap.WorldState
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.components.view.ChildViewComponent
import com.almasb.fxgl.entity.Entity
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import java.util.*

/**
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class AIDebugViewComponent : ChildViewComponent(0.0, -30.0, isTransformApplied = false) {

    private val text = Text()

    private lateinit var goap: GoapComponent

    init {
        text.fill = Color.BLUE
        text.font = Font.font(18.0)

        viewRoot.children.add(text)
    }

    override fun onAdded() {
        goap.listener = object : GoapListener {
            override fun planFound(entity: Entity, goal: WorldState, actions: Queue<GoapAction>) {
                FXGL.debug("Plan found: " + goal + ", " + actions)
            }

            override fun planFailed(entity: Entity, failedGoal: WorldState) {
                FXGL.debug("Plan failed: " + failedGoal)
            }

            override fun actionsFinished(entity: Entity) {
                FXGL.debug("actions finished")
            }

            override fun planAborted(entity: Entity, aborter: GoapAction) {
                FXGL.debug("Plan aborted because of " + aborter)
            }

        }
    }

    override fun onUpdate(tpf: Double) {
        goap.currentAction?.let {
            text.text = it.toString().removeSuffix("Action")
        }
    }
}