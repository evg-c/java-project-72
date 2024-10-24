@import hexlet.code.NamedRoutes
@import hexlet.code.dto.UrlPage
@param UrlPage page = null

@if(page != null && page.getFlash() != null)
    <div class="alert alert-success" role="alert">
        <p>${page.getFlash()}</p>
    </div>
@endif

@template.layout.page(
    content = @`
        <h1>Сайт: ${page.getUrl().getName()}</h1>
        <table>
            <tr>
                <th>ID                </th>
                <th>Название сайта    </th>
                <th>Дата проверки     </th>
                <th>Код проверки      </th>

            </tr>
            <tr>
                <td>${page.getUrl().getId()}</td>
                <td>${page.getUrl().getName()}</td>
                <td>${String.valueOf(page.getLastDateOfCheck())}</td>
                <td>${page.getLastStatusCode()}</td>
            </tr>
        </table>
        <h1>Проверки</h1>
        <form action="${NamedRoutes.urlCheck(page.getUrl().getId())}" method="post">
            <input type="submit" value="Запустить проверку">
        </form>
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)

