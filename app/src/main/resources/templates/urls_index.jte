@import gg.jte.Content
@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <tr>
        <th>ID</th>
        <th>URL</th>
    </tr>
    @for(var urls: page.getUrls())
        <table>
            <tr>
                <td>${urls.getUrl().getId()}</td>
                <td><a href="urls/${urls.getUrl().getId()}">${urls.getUrl().getName()}</a></td>
                <td>${urls.getLastDate()}</td>
                <td>${urls.getStatus_code()}</td>
            </tr>
        </table>
    @endfor
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)

