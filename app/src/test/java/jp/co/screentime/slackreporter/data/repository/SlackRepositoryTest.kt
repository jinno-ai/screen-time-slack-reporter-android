package jp.co.screentime.slackreporter.data.repository

import io.mockk.*
import jp.co.screentime.slackreporter.data.slack.SlackWebhookClient
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SlackRepositoryTest {

    private lateinit var slackWebhookClient: SlackWebhookClient
    private lateinit var repository: SlackRepository

    @Before
    fun setup() {
        slackWebhookClient = mockk()
        repository = SlackRepository(slackWebhookClient)
    }

    @Test
    fun `sendMessage - 送信が成功する`() = runTest {
        val webhookUrl = "https://hooks.slack.com/services/xxx"
        val message = "テストメッセージ"

        coEvery { slackWebhookClient.sendMessage(webhookUrl, message) } returns Result.success(Unit)

        val result = repository.sendMessage(webhookUrl, message)

        assertTrue(result.isSuccess)
        coVerify { slackWebhookClient.sendMessage(webhookUrl, message) }
    }

    @Test
    fun `sendMessage - 送信が失敗する`() = runTest {
        val webhookUrl = "https://hooks.slack.com/services/xxx"
        val message = "テストメッセージ"
        val error = Exception("Network error")

        coEvery { slackWebhookClient.sendMessage(webhookUrl, message) } returns Result.failure(error)

        val result = repository.sendMessage(webhookUrl, message)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sendTestMessage - テスト送信が成功する`() = runTest {
        val webhookUrl = "https://hooks.slack.com/services/xxx"

        coEvery { slackWebhookClient.sendTestMessage(webhookUrl) } returns Result.success(Unit)

        val result = repository.sendTestMessage(webhookUrl)

        assertTrue(result.isSuccess)
        coVerify { slackWebhookClient.sendTestMessage(webhookUrl) }
    }

    @Test
    fun `sendTestMessage - テスト送信が失敗する`() = runTest {
        val webhookUrl = "https://hooks.slack.com/services/xxx"
        val error = Exception("Connection refused")

        coEvery { slackWebhookClient.sendTestMessage(webhookUrl) } returns Result.failure(error)

        val result = repository.sendTestMessage(webhookUrl)

        assertTrue(result.isFailure)
        assertEquals("Connection refused", result.exceptionOrNull()?.message)
    }

    @Test
    fun `sendMessage - SlackWebhookClientに正しく委譲される`() = runTest {
        val webhookUrl = "https://hooks.slack.com/services/T00000000/B00000000/REDACTED-PLACEHOLDER"
        val message = "📊 本日の利用時間レポート"

        coEvery { slackWebhookClient.sendMessage(any(), any()) } returns Result.success(Unit)

        repository.sendMessage(webhookUrl, message)

        coVerify(exactly = 1) {
            slackWebhookClient.sendMessage(
                webhookUrl,
                message
            )
        }
    }
}
