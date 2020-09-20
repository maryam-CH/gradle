/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance.regression.nativeplatform

import org.gradle.initialization.ParallelismBuildOptions
import org.gradle.performance.AbstractCrossVersionPerformanceTest
import org.gradle.performance.categories.SlowPerformanceRegressionTest
import org.gradle.util.Requires
import org.gradle.util.TestPrecondition
import org.junit.experimental.categories.Category

@Category(SlowPerformanceRegressionTest)
@Requires(TestPrecondition.LINUX)
class SwiftCleanBuildPerformanceTest extends AbstractCrossVersionPerformanceTest {

    def setup() {
        runner.minimumBaseVersion = '4.6'
        runner.targetVersions = ["6.7-20200824220048+0000"]
        runner.args += ["--parallel", "--${ParallelismBuildOptions.MaxWorkersOption.LONG_OPTION}=6"]
    }

    def "clean assemble (swift)"() {
        given:
        runner.tasksToRun = ["assemble"]
        runner.cleanTasks = ["clean"]
        runner.gradleOpts = runner.projectMemoryOptions

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }

}
