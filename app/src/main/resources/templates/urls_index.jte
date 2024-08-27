@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлено ни одной страницы</p>
        @else
            @for(var url: page.getUrls())
                <div>
                    <tr>
                        <td>
                            ${url.getId()}
                        </td>
                        <td>
                            <a href="urls/${url.getId()}">${url.getName()}</a>
                        </td>
                    </tr>
                </div>
            @endfor
        @endif
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)

